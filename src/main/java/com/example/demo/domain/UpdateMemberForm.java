package com.example.demo.domain;

import lombok.*;

@Data
public class UpdateMemberForm {
	private String id;
	private String password;
	private String nickName;
	private String email;
	
	private String oldPassword;
}
