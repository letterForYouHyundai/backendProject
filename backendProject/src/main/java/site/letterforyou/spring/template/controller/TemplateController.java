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

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.template.dto.TemplateGetListResponseDTO;
import site.letterforyou.spring.template.dto.TemplatePostLikeResponseDTO;
import site.letterforyou.spring.template.service.TemplateService;

@RestController
@RequestMapping("/template/*")
@Slf4j
public class TemplateController {

	@Autowired
	private TemplateService templateService;
	
	
	@GetMapping("/list")
	public ResponseEntity<ResponseSuccessDTO<TemplateGetListResponseDTO>> getTemplateList(
			@RequestParam(value = "page" , required = false) Long page ,
			@RequestParam(value= "sortBy" ,required = false) String sortBy, 
			@RequestParam(value= "inOrder" ,required = false) Integer inOrder) {

	    Long defaultPage = 1L;
	    String defaultSortBy="likes";
	    int defaultInOrder =1;
	    Long p  = page == null ? defaultPage :page;
	    String sb = sortBy == null? defaultSortBy : sortBy;
	    int io = inOrder == null ? defaultInOrder :inOrder;
	    log.info(": /template/list/" + p);
	    
	    return ResponseEntity.ok(templateService.getTemplateList(sb, io, p));
	}
	
	// 제목 으로 검색 
	@GetMapping("/search")
	public ResponseEntity<ResponseSuccessDTO<TemplateGetListResponseDTO>> getTemplateSearch(
			@RequestParam(value = "keyword" , required = false) String keyword,
			@RequestParam(value = "page" , required = false) Long page ,
			@RequestParam(value= "sortBy" ,required = false) String sortBy, 
			@RequestParam(value= "inOrder" ,required = false) Integer inOrder) {

		String defaultKeyword = "empty";
	    Long defaultPage = 1L;
	    String defaultSortBy="likes";
	    int defaultInOrder =1;
	    Long p  = page == null ? defaultPage :page;
	    String sb = sortBy == null? defaultSortBy : sortBy;
	    int io = inOrder == null ? defaultInOrder :inOrder;
	    String k = keyword == null ? defaultKeyword : keyword;
	    log.info(": /template/list/" + p);
	    
	    return ResponseEntity.ok(templateService.getTemplateSearch(k, sb, io, p));
	}
	
	@PostMapping("/likes/{templateNo}")
	public ResponseEntity<ResponseSuccessDTO<TemplatePostLikeResponseDTO>> postTemplateLike(
			@PathVariable(value="templateNo") Long templateNo , HttpSession session) {
			//String userId = session.getAttribute("");
	    	String userId = "2";
	    return ResponseEntity.ok(templateService.postTemplateLike(templateNo ,userId));
	}
}
