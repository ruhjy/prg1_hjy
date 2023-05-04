package com.example.demo.controller;

import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

import com.example.demo.domain.*;
import com.example.demo.service.*;

import lombok.extern.slf4j.*;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

	private final MemberService service;

	public MemberController(MemberService service) {
		this.service = service;
	}

	@GetMapping("/signup")
	public String signupForm() {
		return "member/signupForm";
	}

	@PostMapping("/signup")
	public String signupProcess(@ModelAttribute("member") Member member, RedirectAttributes redirectAttributes) {

		try {
			service.signup(member);
			redirectAttributes.addFlashAttribute("message", "회원 가입 성공!");
			return "redirect:/list";
		} catch (Exception e) {
			log.error("signup error", e);
			redirectAttributes.addFlashAttribute("member", member);
			redirectAttributes.addFlashAttribute("message", "회원 가입 실패!");
			return "redirect:/member/signup";
		}
	}
	
	@GetMapping("/list")
	public String listForm(Model model) {
		List<Member> memberList = service.listMember();
		model.addAttribute("memberList", memberList);
		return "member/list";
	}
	
	// 경로 : /member/info?id=asdf
	@GetMapping("/info")
	public String memberInfo(@RequestParam("id") String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
		return "member/info";
	}
	

}
