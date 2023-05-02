package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import lombok.extern.slf4j.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BoardService {

	private final BoardMapper2 mapper;

	@Autowired // 생성자 하나일 경우 생략 가능
	public BoardService(BoardMapper2 mapper) {
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

	public boolean modify(Board board) {
		int cnt = mapper.update(board);
		return cnt == 1;
	}

	public boolean remove(Integer id) {
		int cnt = mapper.deleteById(id);
		return cnt == 1;
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
