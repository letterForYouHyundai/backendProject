package site.letterforyou.spring.member.service;

import java.util.Map;

import site.letterforyou.spring.member.domain.MemberDTO;

public interface MemberService {
	
	int selectMemberCnt(MemberDTO mvo);
	
	/**
	 카카오의 accessToken과 RefreshToken을 가져온다. 
	 */
	String getKaKaoAccessAndRefreshToken(String code);
	/**
	 카카오의 accessToken을 이용하여 User정보를 가져온다
	 */
	String getKaKaoUserInfo(String code);
	
	/**
	 User정보를 멤버 테이블에 저장한다.
	 */
	void insertMemberInfo( Map<String, Object> map);
	

}
