package site.letterforyou.spring.board.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {

	private Long boardNo;
	
	private String userId;
	
	private String boardTitle;
	
	private String boardContent;
	
	private LocalDateTime registDate;
	
	private LocalDateTime changeDate;
	
	private String useYn;
	
	private Long boardView;
	
	private String boardThumbNail;
	
	private Long commentCount;
	
	private Long likeCount;
	
	private String userNickname;
	
	private String userImage;
	
	private String likeYn;
}
