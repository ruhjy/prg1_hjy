package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
public class BoardService {

	private final BoardMapper mapper;

	@Autowired // 생성자 하나일 경우 생략 가능
	public BoardService(BoardMapper mapper) {
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

	public boolean addBoard(Board board) {
		int cnt = mapper.insert(board);
		return cnt == 1;
	}

	public Map<String, Object> listBoard(Integer page) {
		// 페이지당 행의 수
		Integer rowPerpage = 15;

		// 쿼리 LIMIT 절에 사용할 시작 인덱스
		Integer startIndex = (page - 1) * rowPerpage;

		// 페이지네이션이 필요한 정보
		// 전체 레코드 수
		Integer numOfRecords = mapper.countAll();
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

		// 게시물 목록
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerpage);
		return Map.of("pageInfo", pageInfo, "boardList", list);
	}
}
