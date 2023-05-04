package com.example.demo.domain;

import java.time.*;

import lombok.*;

@Data
public class Member {
	private String id;
	private String password;
	private String nickName;
	private String email;
	private LocalDateTime inserted;
}
