package site.letterforyou.spring.letter.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LetterVO {
	
	private Long letterNo;
	
	private String letterTitle;
	
	private String letterContent;
	
	private String letterColorNo;
	
	private String senderNickname;
	
	private String receiverNickname;
	
	private LocalDateTime registDate;
}
