package com.example.demo.domain;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
public class Board {
	private Integer id;
	private String title;
	private String body;
	private String writer;
	private Integer hit; // 조회 수
	private LocalDateTime inserted;
	
	private List<String> fileName; // 파일 이미지를 보여주기 위해서 추가
	private Integer fileCount; // 파일의 개수를 보여주기 위해서 추가
	private Integer likeCount; // 좋아요 개수
}
