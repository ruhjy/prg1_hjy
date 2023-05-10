package com.example.demo.service;

import java.util.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

import lombok.extern.slf4j.*;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberService2 {

	private final MemberMapper0510 mapper;

	public MemberService2(MemberMapper0510 mapper) {
		this.mapper = mapper;
	}

	public boolean signup(Member member) {
		int cnt = mapper.insert(member);
		return cnt == 1;
	}

	public List<Member> listMember() {
		return mapper.selectAll();
	}

	public Member get(String id) {
		return mapper.selectById(id);
	}

	public boolean remove(Member member) {
		Member oldMember = mapper.selectById(member.getId());
		int cnt = 0;
		
		if (oldMember.getPassword().equals(member.getPassword())) {
			// 암호가 같으면?
			cnt = mapper.deleteById(member.getId());
		} 
		
		return cnt == 1;
	}

	// 수정 시 이전 비밀번호 확인 기능 추가
	public boolean modify(String memberId, String oldPassword, Member member) {
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
