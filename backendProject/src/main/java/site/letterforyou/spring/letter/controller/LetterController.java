package site.letterforyou.spring.letter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.letter.service.LetterService;

@RestController
@RequestMapping("/letter")
public class LetterController {
	
	@Autowired
	private LetterService LetterService;
	
	@PostMapping("/insertLetter")
	public ResponseEntity<Map<String, Object>> insertLetter(LetterDTO ldto){
		
		
		Map <String, Object> map = new HashMap<String, Object>();
		
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
