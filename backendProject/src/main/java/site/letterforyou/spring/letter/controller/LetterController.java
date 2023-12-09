package site.letterforyou.spring.letter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.letter.dto.LetterDeleteLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetListResponseDTO;
import site.letterforyou.spring.letter.service.LetterService;

@RestController
@RequestMapping("/letter/*")
@Slf4j
public class LetterController {

	@Autowired
	LetterService letterService;

	@GetMapping("/receive/list")
	public ResponseEntity<ResponseSuccessDTO<LetterGetListResponseDTO>> getReceivedLetters(
			@RequestParam(value = "pageNo", required = false) Long page) {

		Long defaultPage = 1L;

		Long p = page == null ? defaultPage : page;

		log.info(": /letter/receive/list"+p);
		// user 검증 추가
		return ResponseEntity.ok(letterService.getLetterReceiveList(p, "user1"));

	}
	
	@GetMapping("/receive/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterGetLetterResponseDTO>> getReceivedLetter(
			@PathVariable(value="letterNo") Long letterNo){
		return ResponseEntity.ok(letterService.getReceivedLetter(letterNo));
	}
	
	@PutMapping("/receive/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterDeleteLetterResponseDTO>> deleteReceivedLetter(
			@PathVariable(value="letterNo") Long letterNo){
		return ResponseEntity.ok(letterService.deleteReceivedLetter(letterNo));
	}
	
	@GetMapping("/send/list")
	public ResponseEntity<ResponseSuccessDTO<LetterGetListResponseDTO>> getSendLetters(
			@RequestParam(value = "pageNo", required = false) Long page) {

		Long defaultPage = 1L;

		Long p = page == null ? defaultPage : page;

		log.info(": /letter/send/list"+p);
		// user 검증 추가
		return ResponseEntity.ok(letterService.getLetterSendList(p, "user2"));

	}
	
	@GetMapping("/send/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterGetLetterResponseDTO>> getSendLetter(
			@PathVariable(value="letterNo") Long letterNo){
		return ResponseEntity.ok(letterService.getSendLetter(letterNo));
	}
	
	@PutMapping("/send/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterDeleteLetterResponseDTO>> deleteSendLetter(
			@PathVariable(value="letterNo") Long letterNo){
		return ResponseEntity.ok(letterService.deleteSendLetter(letterNo));
	}
}