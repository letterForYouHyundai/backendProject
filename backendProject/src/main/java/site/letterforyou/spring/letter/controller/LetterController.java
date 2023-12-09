package site.letterforyou.spring.letter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/letter")
public class LetterController {
	
	@PostMapping("/insertLetter")
	public ResponseEntity<Map<String, Object>> insertLetter(){
		
		
		Map <String, Object> map = new HashMap<String, Object>();
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

}
