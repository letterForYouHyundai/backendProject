package site.letterforyou.spring.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardPostRequestDTO {
	
	private String boardTitle;
	
	private String boardContent;
	
}
