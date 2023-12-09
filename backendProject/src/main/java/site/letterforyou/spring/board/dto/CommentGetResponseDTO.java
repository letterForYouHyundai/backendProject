package site.letterforyou.spring.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetResponseDTO {
	
	private Long commentId; 
	
	private String userNickname;
	
	private String userImage;
	
	private String commentContent;
	
	private String commentDate;
	
	
}
