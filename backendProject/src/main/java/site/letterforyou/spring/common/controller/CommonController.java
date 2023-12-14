package site.letterforyou.spring.common.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.service.CommonService;
import site.letterforyou.spring.member.domain.MemberDTO;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	@ApiOperation(value = "로그인 여부 확인", notes = "로그인 여부를 확인합니다.")
	@GetMapping("/checkLoginYn")
	public ResponseEntity<ResponseSuccessDTO<MemberDTO>> checkLoginYn(@ApiIgnore HttpSession session) {
	  
	    return ResponseEntity.ok(commonService.insertLetter(session));
	}
}
