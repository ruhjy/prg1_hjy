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
public class MemberService {

	private final MemberMapper mapper;

	public MemberService(MemberMapper mapper) {
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
		return mapper.findById(id);
	}
	
	public boolean update(String id, UpdateMemberForm form) {
		int cnt = mapper.update(id, form);
		return cnt == 1;
	}
}
