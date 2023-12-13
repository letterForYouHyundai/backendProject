package site.letterforyou.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.letter.dto.LetterGetLetterResponseDTO;
import site.letterforyou.spring.member.domain.MemberDTO;
import site.letterforyou.spring.member.service.MemberService;

@Controller
@Log4j
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/kakaoLoginPage")
    public ModelAndView kakaoLoginPage(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("member/kakaoLoginPage");

        MemberDTO user = (MemberDTO) session.getAttribute("userInfo");
        if (user != null) {
            mv.addObject("nickname", user.getUserNickname()); // 세션에 있는 닉네임 정보 전달
        }

        return mv;
    }

    @GetMapping("/kakaoLogin")
    @ResponseBody
    public ResponseEntity<ResponseSuccessDTO<MemberDTO>> getKaKaoAccessToken(@RequestParam("code") String code, HttpSession session, HttpServletResponse response) {
     
    	 ResponseSuccessDTO<MemberDTO>  result = memberService.getKaKaoAccessAndRefreshToken(code,session);
 
    	//if(result.getStatus().equals(200))  //return ResponseEntity.ok(result);
    	 return ResponseEntity.ok(result);
    }
    
    @GetMapping("/kakaoLogout")
    public ResponseEntity<ResponseSuccessDTO<MemberDTO>> logout(HttpSession session) {
    
        return ResponseEntity.ok(memberService.kakaoLogout(session));
    }
    @GetMapping("/checkMemberYn")
    @ResponseBody
    public ResponseEntity<ResponseSuccessDTO<MemberDTO>> checkMemberYn(@RequestParam("userEmail") String userEmail) {
    	  log.info(userEmail);
    	  return ResponseEntity.ok(memberService.checkMemberYn(userEmail));	
    }

    @GetMapping("/kakaoRegister")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> kakaoRegister() {
        Map<String, Object> map = new HashMap<>();
        // Do something if needed
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
   
}
