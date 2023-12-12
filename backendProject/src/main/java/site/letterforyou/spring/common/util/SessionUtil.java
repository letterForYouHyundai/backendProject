package site.letterforyou.spring.common.util;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.exception.service.NotAuthorizedUserException;
import site.letterforyou.spring.member.domain.MemberDTO;

@Component
@Slf4j
public class SessionUtil {

	
	public String validSession(HttpSession session) {
		MemberDTO mdto = (MemberDTO) session.getAttribute("userInfo");
		if (mdto == null) {
			throw new NotAuthorizedUserException("허가되지 않은 사용자입니다.");
		}
		
		return mdto.getUserId();
	}
}
