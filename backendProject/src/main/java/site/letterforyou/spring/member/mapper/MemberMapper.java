package site.letterforyou.spring.member.mapper;

import site.letterforyou.spring.member.domain.MemberDTO;

public interface MemberMapper {

	int selectMemberCnt(MemberDTO mvo);
	
	int insertMemberInfo(MemberDTO mvo);

	MemberDTO selectMemberInfo(MemberDTO mdto);
	
	int insertRefreshToken(MemberDTO mdto);
}
