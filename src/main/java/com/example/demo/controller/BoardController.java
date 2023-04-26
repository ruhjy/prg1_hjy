package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

import lombok.extern.slf4j.*;

@Slf4j
@Controller
@RequestMapping("/")
public class BoardController {

	@Autowired
	private BoardService service;

	// 경로 : http://localhost:8080
	// 경로 : http://localhost:8080/list
	// 게시물 목록
//	@RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
	@GetMapping(value = { "/", "/list" })
	public String list(Model model) {
		// 1. request param 수집/가공
		// 2. business logic 처리
		List<Board> list = service.listBoard();

		// 3. add attribute
		model.addAttribute("boardList", list);

		// 4. forward / redirect
		return "list";
	}

	// 특정 게시물 조회
	@GetMapping("/id/{id}")
	public String board(@PathVariable("id") Integer id, Model model) {
		// 1. request param 수집/가공
		// 2. business logic 처리
		Board board = service.getBoard(id);

		// 3. add attribute
		model.addAttribute("board", board);

		// 4. forward / redirect
		return "get";
	}

	// 수정 폼
	@GetMapping("/modify/{id}")
	public String modifyForm(@PathVariable("id") Integer id, Model model) {
		// 1. request param 수집/가공
		// 2. business logic 처리
		Board board = service.getBoard(id);

		// 3. add attribute
		model.addAttribute("board", board);

		// 4. forward / redirect
		return "modify";
	}

	// 수정
//	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	@PostMapping("/modify/{id}")
	public String modifyProcess(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
		boolean ok = service.modify(board);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
			redirectAttributes.addAttribute("id", board.getId());
			redirectAttributes.addAttribute("success", "success");
//			return "redirect:/id/" + board.getId();
			return "redirect:/id/{id}";
		} else {
			// 수정 form 으로 리디렉션
			redirectAttributes.addAttribute("fail", "fail");
			return "redirect:/modify/{id}";
		}
	}

	// 삭제
//	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@PostMapping("/remove")
	public String remove(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		boolean ok = service.remove(id);

		if (ok) {
			redirectAttributes.addAttribute("id", id);
			redirectAttributes.addAttribute("success", "remove");
			return "redirect:/list";
		} else {
			return "redirect:/id/{id}";
		}
	}
	
}
