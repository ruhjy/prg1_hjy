package com.example.demo.controller;

import java.util.*;

import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.util.*;
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

	@GetMapping("/login")
	public String loginForm() {
		return "member/loginForm";
	}
	
	@PreAuthorize("isAnonymous()")
	@GetMapping("/signup")
	public String signupForm() {
		return "member/signupForm";
	}

	@PreAuthorize("isAnonymous()")
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
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/info")
	public String memberInfo(@RequestParam("id") String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
		return "member/info";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/remove")
	public String remove(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {

		boolean ok = service.remove(member);

		if (ok) {
			redirectAttributes.addFlashAttribute("message", "회원 탈퇴 성공!");
			return "redirect:/member/list";
		} else {
			redirectAttributes.addAttribute("id", member.getId());
			redirectAttributes.addFlashAttribute("message", "회원 탈퇴 실패!, 비밀번호 확인...");
//			return "redirect:/member/info?id=" + member.getId();
			return "redirect:/member/info?id={id}";
		}
	}

	// 1. get modify
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify")
	public String modifyForm(@RequestParam String id, Model model) {
		Member member = service.get(id);
		model.addAttribute("member", member);
		return "member/modify";
	}

	// 2. post modify - 수정 시 이전 비밀번호 확인 기능 추가
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify")
	public String modifyMember(
			@RequestParam("id") String memberId,
			@ModelAttribute UpdateMemberForm form,
			RedirectAttributes redirectAttributes) {

		Member member = new Member();
		member.setNickName(form.getNickName());
		member.setEmail(form.getEmail());
		member.setPassword(form.getPassword());

		if (!StringUtils.hasText(form.getPassword())) {
			Member findMember = service.get(memberId);
			member.setPassword(findMember.getPassword());
		}

		// 이전 비밀번호
		String oldPassword = form.getOldPassword();

		boolean ok = service.modify(memberId, oldPassword, member);

		redirectAttributes.addAttribute("id", memberId);
		if (ok) {
			redirectAttributes.addFlashAttribute("message", "회원 수정 성공!");
			return "redirect:/member/info?id={id}";
		} else {
			redirectAttributes.addFlashAttribute("message", "회원 수정 실패!");
			return "redirect:/member/modify?id={id}";
		}
	}

	// 2. post modify
//	@PostMapping("/modify")
	public String modifyMember2(
			@RequestParam("id") String memberId,
			@ModelAttribute UpdateMemberForm form,
			RedirectAttributes redirectAttributes) {

		// form 전송 객체 사용
		Member member = new Member();
		member.setNickName(form.getNickName());
		member.setEmail(form.getEmail());
		member.setPassword(form.getPassword());

		// 비밀번호에 값이 없으면 이전비밀번호 적용
		if (!StringUtils.hasText(form.getPassword())) {
			Member findMember = service.get(memberId);
			member.setPassword(findMember.getPassword());
		}

		boolean ok = service.modify2(memberId, member);

		redirectAttributes.addAttribute("id", memberId);
		if (ok) {
			redirectAttributes.addFlashAttribute("message", "회원 수정 성공!");
			return "redirect:/member/info?id={id}";
		} else {
			redirectAttributes.addFlashAttribute("message", "회원 수정 실패!");
			return "redirect:/member/modify?id={id}";
		}
	}

}
