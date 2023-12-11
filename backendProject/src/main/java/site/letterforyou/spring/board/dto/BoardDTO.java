package site.letterforyou.spring.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO {
	private Long boardNo;
	
	private String userNickname;
		
	private String boardTitle;

	private String image;

	private Long commentCount;

	private Long likeCount;
		
	private String registDate;
		
	private Long boardView;
}
