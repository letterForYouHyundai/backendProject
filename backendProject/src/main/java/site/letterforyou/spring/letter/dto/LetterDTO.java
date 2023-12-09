package site.letterforyou.spring.letter.dto;

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
public class LetterDTO {

	private Long letterNo;
	
	private String letterTitle;
	
	private String letterContent;
	
	private String letterColorNo;
	
	private String senderNickname;
	
	private String receiverNickname;
	
	private String registDate;
	
	
}
