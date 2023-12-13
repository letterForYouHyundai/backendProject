package site.letterforyou.spring.letter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.SessionUtil;
import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.letter.dto.LetterDeleteLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetListResponseDTO;
import site.letterforyou.spring.letter.service.LetterService;
import site.letterforyou.spring.member.domain.MemberDTO;

@RestController
@RequestMapping("/letter")
@Slf4j
public class LetterController {
	
	@Autowired
	private LetterService letterService;
	
	@Autowired
	private SessionUtil sessionUtil;
	
	@PostMapping("/insertLetter")
	public ResponseEntity<ResponseSuccessDTO<LetterDTO>> insertLetter(@RequestBody LetterDTO ldto, HttpSession session) throws Exception{
		
		MemberDTO user = (MemberDTO) session.getAttribute("userInfo");
		
		 //개발 환경 테스트시 하위 코드 주석처리
		 ldto.setLetterSendId(user.getUserId());
		 
		 return ResponseEntity.ok(letterService.insertLetter(ldto));
	}
	
	@GetMapping("/testKaKaoMesage")
	public ResponseEntity<Map<String, Object>> testKaKaoMesage(LetterDTO ldto){
		Map <String, Object> map = new HashMap<String, Object>();

		letterService.sendKaoKaoMessage(ldto);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	
	}
	@GetMapping("/receive/list")
	public ResponseEntity<ResponseSuccessDTO<LetterGetListResponseDTO>> getReceivedLetters(
			@RequestParam(value = "pageNo", required = false) Long page, HttpSession session) {

		Long defaultPage = 1L;

		Long p = page == null ? defaultPage : page;
	
		String userId = sessionUtil.validSession(session);
		
		log.info(": /letter/receive/list"+p);
		
		return ResponseEntity.ok(letterService.getLetterReceiveList(p, userId));

	}
	
	@GetMapping("/receive/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterGetLetterResponseDTO>> getReceivedLetter(
			@PathVariable(value="letterNo") String letterNo){
		return ResponseEntity.ok(letterService.getReceivedLetter(letterNo));
	}
	
	@PutMapping("/receive/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterDeleteLetterResponseDTO>> deleteReceivedLetter(
			@PathVariable(value="letterNo") String letterNo){
		return ResponseEntity.ok(letterService.deleteReceivedLetter(letterNo));
	}
	
	@GetMapping("/send/list")
	public ResponseEntity<ResponseSuccessDTO<LetterGetListResponseDTO>> getSendLetters(
			@RequestParam(value = "pageNo", required = false) Long page, HttpSession session) {

		Long defaultPage = 1L;

		Long p = page == null ? defaultPage : page;

		log.info(": /letter/send/list"+p);
		String userId = sessionUtil.validSession(session);
		return ResponseEntity.ok(letterService.getLetterSendList(p, userId));

	}
	
	@GetMapping("/send/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterGetLetterResponseDTO>> getSendLetter (
			@PathVariable(value="letterNo") String letterNo,HttpSession session){
		sessionUtil.validSession(session);
		return ResponseEntity.ok(letterService.getSendLetter(letterNo));
	}
	
	@PutMapping("/send/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterDeleteLetterResponseDTO>> deleteSendLetter(
			@PathVariable(value="letterNo") String letterNo){
		return ResponseEntity.ok(letterService.deleteSendLetter(letterNo));
	}
}


