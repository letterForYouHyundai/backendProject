package site.letterforyou.spring.member.service;

import java.util.Map;

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
	MemberDTO getKaKaoUserInfo(String refresh_token , String access_token);
	
	/**
	 User정보를 멤버 테이블에 저장한다.
	 */
	MemberDTO insertMemberInfo( Map<String, Object> map);
	
	/**
	 새로운 refresh토큰 정보를 저장한다.
	 */
	int insertRefreshToken(MemberDTO mdto);
	

}
