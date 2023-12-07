package site.letterforyou.spring.board.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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
	
}
