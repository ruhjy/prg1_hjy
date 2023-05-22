package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService service;

	@PostMapping("/add")
	public ResponseEntity<Map<String, Object>> add(@RequestBody Comment comment, Authentication authentication) {

		if (authentication == null) {
			Map<String, Object> res = Map.of("message", "로그인 후 댓글을 작성해주세요.");
			return ResponseEntity.status(401).body(res);
		} else {
			Map<String, Object> res = service.add(comment, authentication);

			return ResponseEntity.ok().body(res);
		}
	}

	@GetMapping("/list")
	public List<Comment> list(@RequestParam("board") Integer boardId, Authentication authentication) {
		return service.list(boardId, authentication);
	}

	@GetMapping("/id/{commentId}")
	public Comment get(@PathVariable Integer commentId) {
		return service.get(commentId);
	}

	@PutMapping("/update")
	@PreAuthorize("authenticated and @customSecurityChecker.checkCommentWriter(authentication, #comment.id)")
	public ResponseEntity<Map<String, Object>> update(@RequestBody Comment comment) {
		Map<String, Object> res = service.update(comment);
		return ResponseEntity.ok().body(res);
	}

//	@RequestMapping(path = "/id/{commentId}", method = RequestMethod.DELETE)
	@DeleteMapping("/id/{commentId}")
	@PreAuthorize("authenticated and @customSecurityChecker.checkCommentWriter(authentication, #commentId)")
	public ResponseEntity<Map<String, Object>> remove(@PathVariable Integer commentId) {
		Map<String, Object> res = service.remove(commentId);
		return ResponseEntity.ok().body(res);
	}

}
