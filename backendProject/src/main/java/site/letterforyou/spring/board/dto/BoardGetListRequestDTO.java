package site.letterforyou.spring.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.letterforyou.spring.board.domain.PageVO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardGetListRequestDTO {

	private String sortBy;
	// 0 : asc, 1: desc
	private int inOrder;

	private PageVO pageVo;
}
