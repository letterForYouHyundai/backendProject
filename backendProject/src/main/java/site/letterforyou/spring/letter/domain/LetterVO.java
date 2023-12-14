package site.letterforyou.spring.letter.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LetterVO {
	
	private Long letterNo;
	
	private String letterTitle;
	
	private String letterContent;
	
	private Long letterColorNo;
	
	private String senderNickname;
	
	private String receiverNickname;
	
	private LocalDateTime registDate;
	
	private String letterReceiveYn;
	
	private String userAlias;
}
