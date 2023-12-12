package site.letterforyou.spring.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateDTO {

	private Long boardNo;
	private String likeYn;
	private String message;
	
}
