package site.letterforyou.spring.board.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AttachVO {

	private Long attachNo;
	
	private Long boardNo;
	
	private LocalDateTime registDate;
	
	private String useYn;
	
	private LocalDateTime changeDate;
	
	private String filePath;
	
	private String originalFileNm;
	
	private String saveFileNm;
}
