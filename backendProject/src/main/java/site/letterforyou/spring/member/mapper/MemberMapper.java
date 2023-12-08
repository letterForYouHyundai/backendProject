package site.letterforyou.spring.member.mapper;

import site.letterforyou.spring.member.domain.UserVO;

public interface MemberMapper {
	
	public UserVO getUserByUserId(String userId);
	
}
