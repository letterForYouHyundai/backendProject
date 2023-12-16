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
public class BoardGetResponseDTO {
	
	private String boardTitle;
	
	private String boardContent;
	
	private String userNickname;
	
	private String boardDate;
	
	private String userImage;
	
	private Long boardLike;
	
	private String likeYn;
	
	private List<String> attachList;
	
	private List<CommentGetResponseDTO> commentList;
	
	private String isWriter;

}
