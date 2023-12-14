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

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.SessionUtil;
import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.letter.dto.LetterDeleteLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetReceiveListResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetSendListResponseDTO;
import site.letterforyou.spring.letter.service.LetterService;
import site.letterforyou.spring.member.domain.MemberDTO;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/letter")
@Slf4j
public class LetterController {

	@Autowired
	private LetterService letterService;

	@Autowired
	private SessionUtil sessionUtil;

	@ApiOperation(value = " 편지 - 편지 작성", notes = "편지를 작성합니다.")
	@PostMapping("/insertLetter")
	public ResponseEntity<ResponseSuccessDTO<LetterDTO>> insertLetter(@RequestBody LetterDTO ldto,
			@ApiIgnore HttpSession session) throws Exception {

		MemberDTO user = (MemberDTO) session.getAttribute("userInfo");

		// 개발 환경 테스트시 하위 코드 주석처리
		ldto.setLetterSendId(user.getUserId());

		return ResponseEntity.ok(letterService.insertLetter(ldto));
	}

	@ApiOperation(value = " 편지 - 카카오 메시지 전송하기 ", notes = " 카카오 URL을 보냅니다. ")
	@GetMapping("/testKaKaoMesage")
	public ResponseEntity<Map<String, Object>> testKaKaoMesage(LetterDTO ldto) {
		Map<String, Object> map = new HashMap<String, Object>();

		letterService.sendKaoKaoMessage(ldto);

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

	}

	@ApiOperation(value = " 편지 - 받은 편지 리스트", notes = " 받은 편지 목록 ")
	@GetMapping("/receive/list")
	public ResponseEntity<ResponseSuccessDTO<LetterGetReceiveListResponseDTO>> getReceivedLetters(
			@RequestParam(value = "page", required = false) Long page, @ApiIgnore HttpSession session) {

		Long defaultPage = 1L;

		Long p = page == null ? defaultPage : page;

		String userId = sessionUtil.validSession(session);
		//String userId = "2";

		log.info(": /letter/receive/list" + p);

		return ResponseEntity.ok(letterService.getLetterReceiveList(p, userId));

	}

	@ApiOperation(value = " 편지 - 받은 편지 상세보기", notes = " 받은 편지에 대한 상세보기 정보를 리턴합니다. ")
	@GetMapping("/receive/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterGetLetterResponseDTO>> getReceivedLetter(
			@PathVariable(value = "letterNo") String letterNo) {
		
		return ResponseEntity.ok(letterService.getReceivedLetter(letterNo));
	}

	@ApiOperation(value = " 편지 - 받은 편지 삭제 ", notes = " 받은 편지를 삭제합니다. ")
	@PutMapping("/receive/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterDeleteLetterResponseDTO>> deleteReceivedLetter(
			@PathVariable(value = "letterNo") String letterNo) {
		return ResponseEntity.ok(letterService.deleteReceivedLetter(letterNo));
	}

	@ApiOperation(value = " 편지 - 보낸 편지 리스트", notes = " 보낸 편지 목록 ")
	@GetMapping("/send/list")
	public ResponseEntity<ResponseSuccessDTO<LetterGetSendListResponseDTO>> getSendLetters(
			@RequestParam(value = "page", required = false) Long page, @ApiIgnore HttpSession session) {

		Long defaultPage = 1L;

		Long p = page == null ? defaultPage : page;

		log.info(": /letter/send/list" + p);
		String userId = sessionUtil.validSession(session);
		
		return ResponseEntity.ok(letterService.getLetterSendList(p, userId));

	}

	@ApiOperation(value = " 편지 - 보낸 편지 상세보기", notes = " 보낸 편지에 대한 상세보기 정보를 리턴합니다. ")
	@GetMapping("/send/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterGetLetterResponseDTO>> getSendLetter(
			@PathVariable(value = "letterNo") String letterNo, @ApiIgnore HttpSession session) {
		String userId = sessionUtil.validSession(session);
		

		return ResponseEntity.ok(letterService.getSendLetter(letterNo));
	}

	@ApiOperation(value = " 편지 - 보낸 편지 삭제 ", notes = " 보낸 편지를 삭제합니다. ")
	@PutMapping("/send/{letterNo}")
	public ResponseEntity<ResponseSuccessDTO<LetterDeleteLetterResponseDTO>> deleteSendLetter(
			@PathVariable(value = "letterNo") String letterNo) {
		return ResponseEntity.ok(letterService.deleteSendLetter(letterNo));
	}
}
