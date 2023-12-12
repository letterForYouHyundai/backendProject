package site.letterforyou.spring.member.domain;

import lombok.Data;

@Data
public class MemberDTO {
	
	/**
	 회원 아이디 
	 */
	private String userId;
	/**
	  회원 이름 
	 */
	private String userName;
	/**
	  회원 이메일
	 */
	private String userEmail;
	/**
	  회원 닉네임
	 */
	private String userNickname;
	/**
	  회원 프로필 이미지 url
	 */
	private String userImage;
	/**
	  회원 가입일자
	 */
	private String  userSignupDt;
	
	/**
	  코드
	 */
	private String code;
	
	/**
	  멤버 이메일 체크
	 */
	private String chkMemberEmail;
	
	/**
	  accessToekn
	 */
	private String accessToken;
	
	/**
	  refreshToken
	 */
	private String refreshToken;
}
