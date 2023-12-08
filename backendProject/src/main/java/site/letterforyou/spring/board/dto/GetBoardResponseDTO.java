package site.letterforyou.spring.board.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardResponseDTO {
	
	private String boardTitle;
	
	private String boardContent;
	
	private String userNickname;
	
	private String boardDate;
	
	private String userImage;
	
	private Long boardLike;
	
	
	private List<String> attachList;
	
	private List<GetCommentResponseDTO> commentList;
	
	
}
