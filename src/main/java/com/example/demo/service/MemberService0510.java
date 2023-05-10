package com.example.demo.service;

import java.util.*;

import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import lombok.*;
import lombok.extern.slf4j.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MemberService0510 {

	private final MemberMapper0510 mapper;
	private final BoardService boardService;
	
	// 시큐리티 적용
	private final PasswordEncoder passwordEncoder;


	// 시큐리티 적용
	public boolean signup(Member member) {
		
		// 암호 암호화
		String plain = member.getPassword();
		member.setPassword(passwordEncoder.encode(plain));
		
		int cnt = mapper.insert(member);
		return cnt == 1;
	}

	public List<Member> listMember() {
		return mapper.selectAll();
	}

	public Member get(String id) {
		return mapper.selectById(id);
	}

	// 시큐리티 적용
	public boolean remove(Member member) {
		Member oldMember = mapper.selectById(member.getId());
		int cnt = 0;
		
		if (passwordEncoder.matches(member.getPassword(), oldMember.getPassword())) {
			// 암호가 같으면?
			
			// 이 회원이 작성한 게시물 row 삭제
			boardService.removeByWriter(member.getId());
			
			// 회원 테이블 삭제
			cnt = mapper.deleteById(member.getId());
		} 
		
		return cnt == 1;
	}
	
	// 시큐리티 적용
	public boolean modify(String memberId, String oldPassword, Member member) {
		log.info("modify");
		// 패스워드를 바꾸기 위해 입력했다면...
		if (!member.getPassword().isBlank()) {
			// 입력된 패스워드를 암호화
			String plain = member.getPassword();
			member.setPassword(passwordEncoder.encode(plain));
		}
		
		Member oldMember = mapper.selectById(memberId);
		
		int cnt = 0;
		
		// 이전 비밀번호가 같으면 수정
		if (passwordEncoder.matches(oldPassword, oldMember.getPassword())) {
			cnt = mapper.update(memberId, member);
		}
		
		return cnt == 1;
	}

	// 수정 시 이전 비밀번호 확인 기능 추가
	public boolean modify3(String memberId, String oldPassword, Member member) {
		Member oldMember = mapper.selectById(memberId);
		
		int cnt = 0;
		
		// 이전 비밀번호가 같으면 수정
		if (oldMember.getPassword().equals(oldPassword)) {
			cnt = mapper.update2(memberId, member);
		}
		return cnt == 1;
	}
	
	public boolean modify2(String memberId, Member member) {
		int cnt = mapper.update2(memberId, member);
		return cnt == 1;
	}

}
