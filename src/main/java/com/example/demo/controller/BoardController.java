package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

import lombok.extern.slf4j.*;
import software.amazon.awssdk.services.s3.*;

@Slf4j
@Controller
@RequestMapping("/")
public class BoardController {
	
	@Autowired
	private BoardService service;

	// pagination 적용
	// 경로 : http://localhost:8080?page=3
	// 경로 : http://localhost:8080/list?page=3
	// 게시물 목록
	@GetMapping(value = { "/", "/list" })
	public String list(Model model,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "maxPageSize", defaultValue = "15") Integer pageSize,
			@RequestParam(value = "type", required = false) String type) {
		// 1. request param 수집/가공
		// 2. business logic 처리
		Map<String, Object> result = service.listBoard(page, search, type, pageSize);

		// 3. add attribute
//		model.addAttribute("boardList", result.get("boardList"));
//		model.addAttribute("pageInfo", result.get("pageInfo"));
		// 서비스에 받은 정보 한번에 담기
		model.addAllAttributes(result);

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
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #id)")
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
	// 수정하려는 게시물 id = board.id
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #board.id)")
	@PostMapping("/modify/{id}")
	public String modifyProcess(
			@RequestParam(value = "files", required = false) MultipartFile[] addFiles,
			@RequestParam(value = "removeFiles", required = false) List<String> removeFileNames,
			@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
		
		boolean ok = service.modify(board, addFiles, removeFileNames);

		if (ok) {
			// 해당 게시물 보기로 리디렉션
			redirectAttributes.addAttribute("id", board.getId());
			// query string에 추가
//			redirectAttributes.addAttribute("success", "success");

			// 메세지 모델에 추가
			redirectAttributes.addFlashAttribute("message", board.getId() + "번 글이 수정되었습니다.");
//			return "redirect:/id/" + board.getId();
			return "redirect:/id/{id}";
		} else {
			// 수정 form 으로 리디렉션
			redirectAttributes.addAttribute("fail", "fail");
			redirectAttributes.addFlashAttribute("message", board.getId() + "번 글 수정 중에 문제 발생@");
			return "redirect:/modify/{id}";
		}
	}

	// 삭제
//	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated() and @customSecurityChecker.checkBoardWriter(authentication, #id)")
	@PostMapping("/remove")
	public String remove(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		boolean ok = service.remove(id);

		if (ok) {
			redirectAttributes.addAttribute("id", id);
			// query string에 추가
//			redirectAttributes.addAttribute("success", "remove");

			// 메세지 모델에 추가
			redirectAttributes.addFlashAttribute("message", id + "번 글이 삭제되었습니다.");
			return "redirect:/list";
		} else {
			redirectAttributes.addFlashAttribute("message", id + "번 글 삭제 중 문제 발생!");
			return "redirect:/id/{id}";
		}
	}

	// 추가 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/add")
	public String addForm(@ModelAttribute Board board) {
		// 반환 타입 void로 할 경우 return 생략 가능
		// 게시물 작성 form (view)로 포워드
		return "add";
	}

	// 추가
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add")
	public String addProcess(
			@RequestParam("files") MultipartFile[] files,
			@ModelAttribute Board board,
			RedirectAttributes redirectAttributes,
			Authentication authentication) {
		// 새 게시물 db에 추가
		// 2.
		board.setWriter(authentication.getName());
		boolean ok = service.addBoard(board, files);
		// 3.
		// 4.
		if (ok) {
			// 메세지 모델에 추가
			redirectAttributes.addFlashAttribute("message", board.getId() + "번 글이 등록되었습니다.");

			return "redirect:/id/" + board.getId();
		} else {
			redirectAttributes.addFlashAttribute("board", board);
			redirectAttributes.addFlashAttribute("message", "글 등록 중 문제가 발생하였습니다.");
			return "redirect:/add";
		}
	}

}
