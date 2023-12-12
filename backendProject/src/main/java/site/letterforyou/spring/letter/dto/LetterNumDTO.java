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
public class LetterNumDTO {
	private String letterNo;
	
	private String LetterReceiveYn;
}
