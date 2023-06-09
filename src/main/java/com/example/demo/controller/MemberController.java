package com.example.demo.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

import jakarta.servlet.http.*;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService service;

	// 회원가입 & 회원 수정
	// 아이디 중복 체크
	@ResponseBody
	@GetMapping("/checkId/{id}")
	public Map<String, Object> checkId(@PathVariable String id) {
		return service.checkId(id);
//		return Map.of("available", true);
	}
	
	// 별명 중복 체크
	@ResponseBody
	@GetMapping("/checkNickName/{nickName}")
	public Map<String, Object> checkNickName(@PathVariable String nickName, Authentication authentication) {
		return service.checkNickName(nickName, authentication);
	}
	
	// 이메일 중복 체크
	@ResponseBody
	@GetMapping("/checkEmail/{email}")
	public Map<String, Object> checkEmail(@PathVariable String email, Authentication authentication) {
		return service.checkEmail(email, authentication);
	}

	@GetMapping("signup")
	@PreAuthorize("isAnonymous()")
	public void signupForm() {

	}

	@GetMapping("login")
	public void loginForm() {

	}

	@PostMapping("signup")
	@PreAuthorize("isAnonymous()")
	public String signupProcess(Member member, RedirectAttributes rttr) {

		try {
			service.signup(member);
			rttr.addFlashAttribute("message", "회원 가입되었습니다.");
			return "redirect:/list";
		} catch (Exception e) {
			e.printStackTrace();
			rttr.addFlashAttribute("member", member);
			rttr.addFlashAttribute("message", "회원 가입 중 문제가 발생했습니다.");
			return "redirect:/member/signup";
		}
	}

	@GetMapping("list")
	public void list(Model model) {
		List<Member> list = service.listMember();
		model.addAttribute("memberList", list);
	}

	// 경로: /member/info?id=asdf
	@GetMapping("info")
	@PreAuthorize("hasAuthority('admin') or isAuthenticated() and (authentication.name eq #id)")
	public void info(String id, Model model) {

		Member member = service.get(id);
		model.addAttribute("member", member);

	}

	@PostMapping("remove")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #member.id)")
	public String remove(
			@ModelAttribute Member member,
			RedirectAttributes rttr,
			HttpServletRequest request) throws Exception {

		boolean ok = service.remove(member);

		if (ok) {
			rttr.addFlashAttribute("message", "회원 탈퇴하였습니다.");

			// 로그아웃
			request.logout();

			return "redirect:/list";
		} else {
			rttr.addFlashAttribute("message", "회원 탈퇴시 문제가 발생하였습니다.");
			return "redirect:/member/info?id=" + member.getId();
		}
	}

	// 1.
	@GetMapping("modify")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #id)")
	public void modifyForm(String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
//		model.addAttribute(service.get(id));

	}

	// 2.
	@PostMapping("modify")
	@PreAuthorize("isAuthenticated() and (authentication.name eq #member.id) ")
	public String modifyProcess(Member member, String oldPassword, RedirectAttributes rttr) {
		boolean ok = service.modify(member, oldPassword);

		if (ok) {
			rttr.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
			return "redirect:/member/info?id=" + member.getId();
		} else {
			rttr.addFlashAttribute("message", "회원 정보 수정시 문제가 발생하였습니다.");
			return "redirect:/member/modify?id=" + member.getId();
		}

	}
	
}
