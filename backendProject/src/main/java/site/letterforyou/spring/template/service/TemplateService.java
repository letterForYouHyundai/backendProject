package site.letterforyou.spring.template.service;

import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.template.dto.TemplateGetListResponseDTO;

public interface TemplateService {

	ResponseSuccessDTO<TemplateGetListResponseDTO> getTemplateList(String sortBy, int inOrder, Long page);

	ResponseSuccessDTO<TemplateGetListResponseDTO> getTemplateSearch(String keyword, String sortBy, int inOrder, Long page);
	

}
