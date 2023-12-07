package site.letterforyou.spring.board.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentVO {

	private Long commentId;
	private Long board_no;
	private String userId;
	private String commentContent;
	private LocalDateTime registDate;
	private LocalDateTime changeDate;
	private String useYn;
	
}
