package site.letterforyou.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j;
import site.letterforyou.spring.member.domain.MemberDTO;
import site.letterforyou.spring.member.service.MemberService;

@RestController
@RequestMapping("/member")
@Log4j
public class MemberController {

	
	@Autowired
	private MemberService memberService;
	
	
	@GetMapping("/kakaoRegister")
	public ResponseEntity<Map<String, Object>> kakaoRegister() {
		
		Map <String, Object> map = new HashMap<String, Object>();
		
		 return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	//추후 삭제에정
	@GetMapping("/kakaoLoginPage")
	public ModelAndView kakaoLoginPage() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("member/kakaoLoginPage");
		
		return mv;
	}
	
	@GetMapping("/getKaKaoAccessToken")
	public ResponseEntity<Map<String, Object>> getKaKaoAccessToken(@RequestParam("code") String code) {
		 Map <String, Object> map = new HashMap<String, Object>();
		
		log.info("code: "+code);
		
		memberService.getKaKaoAccessAndRefreshToken(code);
		 
		 return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
