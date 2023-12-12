package site.letterforyou.spring.board.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardLikeVO {
	private Long boardLikeNo;
	private String userId;
	private Long boardNo;
	private String likeYn;
	private LocalDateTime registDate;
	private LocalDateTime changeDate;
 }
