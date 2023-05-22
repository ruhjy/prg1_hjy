package com.example.demo.security;

import org.springframework.security.core.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import lombok.*;
import lombok.extern.slf4j.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSecurityChecker {

	private final BoardMapper boardMapper;
	private final CommentMapper commentMapper;
	
	public boolean checkCommentWriter(Authentication authentication, Integer commentId) {
		Comment comment = commentMapper.selectById(commentId);
		
		return comment.getMemberId().equals(authentication.getName()); 
	}
	
	public boolean checkBoardWriter(Authentication authentication, Integer boardId) {
		log.info("checkBoardWriter 호출");
		Board board = boardMapper.selectById(boardId);
		
		String username = authentication.getName();
		String writer = board.getWriter();
		
		return username.equals(writer);
	}
	
}
