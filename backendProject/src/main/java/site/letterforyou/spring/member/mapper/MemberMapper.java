package site.letterforyou.spring.member.mapper;

import site.letterforyou.spring.member.domain.MemberDTO;

public interface MemberMapper {

	int selectMemberCnt(MemberDTO mvo);

}
