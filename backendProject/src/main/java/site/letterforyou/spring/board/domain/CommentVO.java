package site.letterforyou.spring.board.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CommentVO {

	private Long commentId;
	private Long boardNo;
	private String userId;
	private String userNickname;
	private String userImage;
	private String commentContent;
	private LocalDateTime registDate;
	private LocalDateTime changeDate;
	private String useYn;
	
}
