package site.letterforyou.spring.letter.dto;

import java.util.List;

import lombok.Data;
import site.letterforyou.spring.common.domain.Pagination;

@Data

public class LetterGetListResponseDTO {

	private List<Letterdtos> letterList;
	
	private Pagination pagination;
}
