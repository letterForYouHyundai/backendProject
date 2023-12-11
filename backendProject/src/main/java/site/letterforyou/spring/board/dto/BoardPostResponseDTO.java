package site.letterforyou.spring.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardPostResponseDTO {
	private String message; 
}
