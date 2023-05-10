package com.example.demo.domain;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
public class Member {
	private String id;
	private String password;
	private String nickName;
	private String email;
	private LocalDateTime inserted;
	
	// 권한
	List<String> authority;
}
