package site.letterforyou.spring.letter.dto;

import java.util.List;

import lombok.Data;
import site.letterforyou.spring.common.domain.Pagination;

@Data

public class LetterGetSendListResponseDTO {

	private List<LetterSendDTO> letterList;
	
	private Pagination pagination;
}
