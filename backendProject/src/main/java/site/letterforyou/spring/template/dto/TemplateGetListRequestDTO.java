package site.letterforyou.spring.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.letterforyou.spring.common.domain.PageVO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemplateGetListRequestDTO {

	
	private String sortBy;
	// 0 : asc, 1: desc
	private int inOrder;

	private PageVO pageVo;
}
