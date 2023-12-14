package site.letterforyou.spring.member.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.letter.dto.LetterGetReceiveListResponseDTO;
import site.letterforyou.spring.member.domain.MemberDTO;

public interface MemberService {
	
	int selectMemberCnt(MemberDTO mvo);
	
	/**
	 카카오의 accessToken과 RefreshToken을 가져온다. 
	 * @param session 
	 */
	
	public ResponseSuccessDTO<MemberDTO> getKaKaoAccessAndRefreshToken(String code, HttpSession session);	
	////MemberDTO getKaKaoAccessAndRefreshToken(String code);
	
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
	public ResponseSuccessDTO<MemberDTO>  kakaoLogout(HttpSession session);
	
	/**
	   회원여부를 조회한다.
	 */
	public ResponseSuccessDTO<MemberDTO>  checkMemberYn(String userEmail);
}
