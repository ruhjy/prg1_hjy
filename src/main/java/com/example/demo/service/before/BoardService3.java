package com.example.demo.service.before;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import lombok.extern.slf4j.*;
import software.amazon.awssdk.services.s3.*;

@Slf4j
public class BoardService3 {

	private final S3Client s3;
	private final BoardMapper mapper;

	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Autowired // 생성자 하나일 경우 생략 가능
	public BoardService3(S3Client s3, BoardMapper mapper) {
		this.s3 = s3;
		this.mapper = mapper;
	}

	public List<Board> listBoard() {
		List<Board> list = mapper.selectAll();
		return list;
	}

	public Board getBoard(Integer id) {
		Board board = mapper.selectById(id);
		return board;
	}

	public boolean modify(Board board, MultipartFile[] addFiles, List<String> removeFileNames) {

		// FileName 테이블 삭제
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {
				// 하드디스크에서 삭제
				String path = "C:\\study\\upload\\" + board.getId() + "\\" + fileName;
				File file = new File(path);

				if (file.exists()) {
					file.delete();
				}

				// 테이블에서 삭제
				mapper.deleteFileNameByBoardIdAndFileName(board.getId(), fileName);
			}
		}

		// 새 파일 추가
		for (MultipartFile newFile : addFiles) {
			if (newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				mapper.insertFileName(board.getId(), newFile.getOriginalFilename());

				String fileName = newFile.getOriginalFilename();
				String folder = "C:\\study\\upload\\" + board.getId();
				String path = folder + "\\" + fileName;

				// 폴더 없으면 만들기
				File dir = new File(folder);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				// 파일을 하드디스크에 저장
				try {
					File file = new File(path);
					newFile.transferTo(file);
				} catch (IllegalStateException e) {
					log.error("modify error%%%%", e);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		// 게시물(Board) 테이블 수정
		int cnt = mapper.update(board);

		return cnt == 1;
	}

	public boolean remove(Integer id) {

		// 파일명 조회
		List<String> fileNames = mapper.selectFileNamesByBoardId(id);

		// FileName 테이블의 데이터 지우기
		mapper.deleteFileNameByBoardId(id);

		// 하드디스크의 파일 지우기
		deleteFile(id, fileNames);

		// 게시물 테이블의 데이터 지우기
		int cnt = mapper.deleteById(id);

		return cnt == 1;
	}

	private void deleteFile(Integer id, List<String> fileNames) {
		for (String fileName : fileNames) {
			String path = "C:\\study\\upload\\" + id + "\\" + fileName;
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	// 2. 트랜잭션 처리하기 - 클래스 레벨에 @Transactional 선언
	public boolean addBoard(Board board, MultipartFile[] files) {
		// 게시물 insert
		int cnt = mapper.insert(board);

		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
				// 파일 저장 (파일 시스템에)
				String folder = "C:\\study\\upload\\" + board.getId();

				// 1. 게시물번호 폴더 만들기
				File targetFolder = new File(folder);
				if (!targetFolder.exists()) {
					targetFolder.mkdirs();
				}

				String path = folder + "\\" + file.getOriginalFilename();
				log.info("path={}", path);

				File target = new File(path);
				try {
					file.transferTo(target);

				} catch (Exception e) {
					log.error("file upload error= ", e);
					throw new RuntimeException(e);
				}
				// db에 관련 정보 저장(insert)
				mapper.insertFileName(board.getId(), file.getOriginalFilename());

			}
		}

		return cnt == 1;
	}

	public Map<String, Object> listBoard(
			Integer page, String search, String type, Integer pageSize) {

		// 페이지당 행의 수
		Integer rowPerpage = 15;
		if (pageSize != null) {
			rowPerpage = pageSize;
		}

		// 쿼리 LIMIT 절에 사용할 시작 인덱스
		Integer startIndex = (page - 1) * rowPerpage;

		// 페이지네이션이 필요한 정보
		// 전체 레코드 수
		Integer numOfRecords = mapper.countAll(search, type);
		// 마지막 페이지 번호
		Integer lastPageNumber = (numOfRecords - 1) / rowPerpage + 1;

		// 페이지네이션 왼쪽 번호
		Integer leftPageNumber = page - 5;
		// 1보다 작을 수 없다.
		leftPageNumber = Math.max(leftPageNumber, 1);

		// 페이지네이션 오른쪽 번호
		Integer rightPageNumber = leftPageNumber + 9;
		// 마지막 페이지보다 클 수 없다.
		rightPageNumber = Math.min(rightPageNumber, lastPageNumber);

		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("leftPageNum", leftPageNumber);
		pageInfo.put("rightPageNum", rightPageNumber);
		pageInfo.put("currentPageNum", page);
		pageInfo.put("lastPageNum", lastPageNumber);
		pageInfo.put("numOfRecords", numOfRecords);

		// 게시물 목록
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerpage, search, type);
		return Map.of("pageInfo", pageInfo, "boardList", list);
	}
}
