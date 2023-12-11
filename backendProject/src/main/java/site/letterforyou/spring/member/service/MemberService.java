package site.letterforyou.spring.member.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import site.letterforyou.spring.member.domain.MemberDTO;

public interface MemberService {
	
	int selectMemberCnt(MemberDTO mvo);
	
	/**
	 카카오의 accessToken과 RefreshToken을 가져온다. 
	 */
	MemberDTO getKaKaoAccessAndRefreshToken(String code);
	/**
	 카카오의 accessToken을 이용하여 User정보를 가져온다
	 */
	MemberDTO getKaKaoUserInfo(String code);
	
	/**
	 User정보를 멤버 테이블에 저장한다.
	 */
	MemberDTO insertMemberInfo( Map<String, Object> map);

	/**
	 카카오정보를 로그아웃한다.
	 */
	int  kakaoLogout(MemberDTO mdto);
	

}
