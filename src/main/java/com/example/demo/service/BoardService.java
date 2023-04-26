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
}
