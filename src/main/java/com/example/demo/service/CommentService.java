package com.example.demo.service;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService {

	@Autowired
	private CommentMapper mapper;

	public List<Comment> list(Integer boardId, Authentication authentication) {
		List<Comment> comments = mapper.selectAllByBoardId(boardId);
		
		if (authentication != null) {
//			List<Comment> commentsWithEditable = comments.stream()
//					.map(c -> {
//						c.setEditable(c.getMemberId().equals(authentication.getName()));
//						return c;
//					}).toList();
			for (Comment comment : comments) {
				comment.setEditable(comment.getMemberId().equals(authentication.getName()));
			}
		}
		
		return comments;
		
	}

	public Map<String, Object> add(Comment comment, Authentication authentication) {
		comment.setMemberId(authentication.getName());

		Map<String, Object> res = new HashMap<>();

		int cnt = mapper.insert(comment);
		if (cnt == 1) {
			res.put("message", "댓글이 등록되었습니다.");
		} else {
			res.put("message", "댓글이 등록되지 않았습니다.");
		}

		return res;
	}

	public Map<String, Object> remove(Integer commentId) {
		Map<String, Object> res = new HashMap<>();

		int cnt = mapper.deleteById(commentId);

		if (cnt == 1) {
			res.put("message", "댓글이 삭제되었습니다.");
		} else {
			res.put("message", "댓글이 삭제되지 않았습니다.");
		}

		return res;
	}

	public Comment get(Integer commentId) {
		return mapper.selectById(commentId);
	}

	public Map<String, Object> update(Comment comment) {
		Map<String, Object> res = new HashMap<>();

		int cnt = mapper.update(comment);
		if (cnt == 1) {
			res.put("message", "댓글이 수정되었습니다.");
		} else {
			res.put("message", "댓글이 수정되지 않았습니다.");
		}

		return res;
	}

}
