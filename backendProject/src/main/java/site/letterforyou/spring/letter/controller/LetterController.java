package site.letterforyou.spring.letter.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.letter.service.LetterService;
import site.letterforyou.spring.member.domain.MemberDTO;

@RestController
@RequestMapping("/letter")
public class LetterController {
	
	@Autowired
	private LetterService LetterService;
	
	@PostMapping("/insertLetter")
	public ResponseEntity<Map<String, Object>> insertLetter(LetterDTO ldto, HttpSession session){
		
		MemberDTO user = (MemberDTO) session.getAttribute("userInfo");
		
		Map <String, Object> map = new HashMap<String, Object>();
		//ldto.setLetterSendId(user.getUserId());
		LetterService.insertLetter(ldto);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@GetMapping("/testKaKaoMesage")
	public ResponseEntity<Map<String, Object>> testKaKaoMesage(LetterDTO ldto){
		Map <String, Object> map = new HashMap<String, Object>();

		LetterService.sendKaoKaoMessage(ldto);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	
	}

}
