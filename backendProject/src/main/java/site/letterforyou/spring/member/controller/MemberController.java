package site.letterforyou.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import site.letterforyou.spring.member.domain.MemberDTO;
import site.letterforyou.spring.member.service.MemberService;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getKaKaoAccessToken(@RequestParam("code") String code, HttpSession session, HttpServletResponse response) {
        
    	Map<String, Object> map = new HashMap<>();
        MemberDTO mdto = memberService.getKaKaoAccessAndRefreshToken(code);

        if (mdto.getAccessToken() != null && !mdto.getAccessToken().isEmpty()) {
            session.setAttribute("userInfo", mdto);
            map.put("message", "Login successful");
            map.put("userInfo", mdto);
            return new ResponseEntity<>(map, HttpStatus.OK);
            
        } else {
            map.put("message", "Login failed");
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/kakaoLogout")
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo"); 
        return "redirect:/member/kakaoLoginPage";
    }

    @GetMapping("/kakaoRegister")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> kakaoRegister() {
        Map<String, Object> map = new HashMap<>();
        // Do something if needed
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
