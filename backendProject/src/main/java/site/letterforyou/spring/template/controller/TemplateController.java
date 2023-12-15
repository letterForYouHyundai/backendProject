package site.letterforyou.spring.template.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.common.dto.PageRequestDTO;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.PageUtil;
import site.letterforyou.spring.common.util.SessionUtil;
import site.letterforyou.spring.template.dto.TemplateGetListResponseDTO;
import site.letterforyou.spring.template.dto.TemplatePostLikeResponseDTO;
import site.letterforyou.spring.template.service.TemplateService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/template/*")
@Slf4j
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private PageUtil pageUtil;
	
	@Autowired
	private SessionUtil sessionUtil;
	
	@ApiOperation(value = " 편지 템플릿 - 편지 템플릿 리스트 ", notes = " 편지 템플릿 리스트를 리턴합니다. ")
	@GetMapping("/list")
	public ResponseEntity<ResponseSuccessDTO<TemplateGetListResponseDTO>> getTemplateList(
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "inOrder", required = false) Integer inOrder) {

		PageRequestDTO pageDTO = pageUtil.parsePaginationComponents(page, sortBy, inOrder);

		return ResponseEntity
				.ok(templateService.getTemplateList(pageDTO.getSortBy(), pageDTO.getInOrder(), pageDTO.getPage()));
	}

	
	@ApiOperation(value = " 편지 템플릿 - 편지 템플릿 검색 리스트 ", notes = " 편지 템플릿을 키워드로 검색한 결과를 리턴합니다. ")
	@GetMapping("/search")
	public ResponseEntity<ResponseSuccessDTO<TemplateGetListResponseDTO>> getTemplateSearch(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Long page,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "inOrder", required = false) Integer inOrder) {

		PageRequestDTO pageDTO = pageUtil.parsePaginationComponents(page, sortBy, inOrder);
		String defaultKeyword = "empty";
		String k = keyword == null ? defaultKeyword : keyword;

		return ResponseEntity
				.ok(templateService.getTemplateSearch(k, pageDTO.getSortBy(), pageDTO.getInOrder(), pageDTO.getPage()));
	}
	
	@ApiOperation(value = " 편지 템플릿 - 편지 템플릿 추천/비추천 ", notes = " 편지 템플릿을 추천/비추천을 누릅니다. ")
	@PostMapping("/likes/{templateNo}")
	public ResponseEntity<ResponseSuccessDTO<TemplatePostLikeResponseDTO>> postTemplateLike(
			@PathVariable(value = "templateNo") Long templateNo, @ApiIgnore HttpSession session) {
		 String userId = sessionUtil.validSession(session);
		
		return ResponseEntity.ok(templateService.postTemplateLike(templateNo, userId));
	}
}
