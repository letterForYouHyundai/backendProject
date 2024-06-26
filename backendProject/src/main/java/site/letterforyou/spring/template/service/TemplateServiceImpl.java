package site.letterforyou.spring.template.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.common.domain.PageVO;
import site.letterforyou.spring.common.domain.Pagination;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.ResponseUtil;
import site.letterforyou.spring.common.util.TimeService;
import site.letterforyou.spring.exception.service.BadVariableRequestException;
import site.letterforyou.spring.exception.service.EntityNullException;
import site.letterforyou.spring.template.domain.TemplateVO;
import site.letterforyou.spring.template.dto.TemplateDTO;
import site.letterforyou.spring.template.dto.TemplateGetListResponseDTO;
import site.letterforyou.spring.template.dto.TemplatePostLikeResponseDTO;
import site.letterforyou.spring.template.mapper.TemplateMapper;

@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService{

	@Autowired
	private ResponseUtil responseUtil;
	
	@Autowired
	private TemplateMapper templateMapper;
	
	@Autowired
	private TimeService timeService;

	@Override
	public ResponseSuccessDTO<TemplateGetListResponseDTO> getTemplateList(String sortBy, int inOrder, Long page) {
		
		PageVO pageVo = new PageVO(page, 6L, 10L);
		if(sortBy ==null) {
			inOrder =1;
			sortBy="DESC";
	    }
		pageVo.setSortBy(sortBy);
		
		pageVo.setOrderBy(inOrder == 0 ? "ASC" : "DESC");

		
		Long offset = pageVo.getOffset();
		Long size = pageVo.getRecordSize();
		List<TemplateVO> templateVoList = templateMapper.getTemplateList(pageVo.getSortBy(), pageVo.getOrderBy(), offset,size);
		
		
		TemplateGetListResponseDTO result = new TemplateGetListResponseDTO();
		
		List<TemplateDTO> templateList = new ArrayList<>();
		for(TemplateVO t : templateVoList) {
			TemplateDTO templateDTO = new TemplateDTO();
			
			templateDTO.setTemplateNo(t.getTemplateNo());
			templateDTO.setTemplateTitle(t.getTemplateTitle());
			templateDTO.setTemplateContent(t.getTemplateContent());
			templateDTO.setRegistDate(timeService.parseLocalDateTimeForLetter(t.getRegistDate()));
			templateDTO.setTemplateLikes(t.getTemplateLikes());
			
			templateList.add(templateDTO);
		}
		
		result.setTemplateList(templateList);
		int count = templateMapper.getTotalCountTemplate();
		Pagination pagination = new Pagination(count, pageVo);
		result.setPagination(pagination);
		ResponseSuccessDTO<TemplateGetListResponseDTO> res =  responseUtil.successResponse( result, HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<TemplateGetListResponseDTO> getTemplateSearch(String keyword, String sortBy, int inOrder,
			Long page) {
		if(keyword.equals("empty")) {
			throw new BadVariableRequestException("Keyword 가 비어있습니다.");
		}
		
		PageVO pageVo = new PageVO(page, 9L, 10L);
		if(sortBy ==null) {
			inOrder =1;
			sortBy="DESC";
	    }
		pageVo.setSortBy(sortBy);
		
		pageVo.setOrderBy(inOrder == 0 ? "ASC" : "DESC");
		
		Long offset = pageVo.getOffset();
		Long size = pageVo.getRecordSize();
		List<TemplateVO> templateVoList = templateMapper.getTemplateSearchList(keyword, pageVo.getSortBy(), pageVo.getOrderBy(), offset,size);
		
		TemplateGetListResponseDTO result = new TemplateGetListResponseDTO();
		List<TemplateDTO> templateList = new ArrayList<>();
		for(TemplateVO t : templateVoList) {
			TemplateDTO templateDTO = new TemplateDTO();
			
			templateDTO.setTemplateNo(t.getTemplateNo());
			templateDTO.setTemplateTitle(t.getTemplateTitle());
			templateDTO.setTemplateContent(t.getTemplateContent());
			templateDTO.setRegistDate(timeService.parseLocalDateTimeForLetter(t.getRegistDate()));
			templateDTO.setTemplateLikes(t.getTemplateLikes());
			
			templateList.add(templateDTO);
		}
		
		result.setTemplateList(templateList);
		int count = templateMapper.getTotalCountTemplateByKeyword(keyword);
		Pagination pagination = new Pagination(count, pageVo);
		result.setPagination(pagination);
		ResponseSuccessDTO<TemplateGetListResponseDTO> res =  responseUtil.successResponse(result, HttpStatus.OK);
		return res;
	}

	@Override
	public ResponseSuccessDTO<TemplatePostLikeResponseDTO> postTemplateLike(Long templateNo, String userId) {
		userId="user2";
		if(userId == null) {
			throw new EntityNullException("아이디가 비어 있습니다.");
		}
		TemplatePostLikeResponseDTO result = new  TemplatePostLikeResponseDTO();
		templateMapper.updateTemplateLike(templateNo, userId);
		
		TemplateVO templateVo = templateMapper.getTemplate(templateNo);
		result.setLikeYn(templateVo.getLikeYn());
		result.setMessage(userId+" 아이디의 "+templateNo+" 번 Template like가 post 되었습니다");
		ResponseSuccessDTO<TemplatePostLikeResponseDTO> res =  responseUtil.successResponse(result, HttpStatus.OK);
		return res;
	}
	
	
	
	
}
