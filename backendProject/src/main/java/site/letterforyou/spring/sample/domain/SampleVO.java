package site.letterforyou.spring.sample.domain;

import lombok.Data;

@Data
public class SampleVO {

	/**
	 회원 아이디
	 */
	private String userId;

	/**
	 회원 비밀번호
	 */
	private String userPw;
	
	/**
	 회원 이름
	 */
	private String name;
}
