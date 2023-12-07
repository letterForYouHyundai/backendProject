package site.letterforyou.spring.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardListResponseDTO {

private Long boardNo;
	
private String userNickname;
	
private String boardTitle;

private String image;

private Long commentCount;

private Long likeCount;
	
private String registDate;
	
private Long boardView;
	
}
