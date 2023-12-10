package site.letterforyou.spring.letter.mapper;

import java.lang.reflect.Member;

import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.member.domain.MemberDTO;

public interface LetterMapper {

	/**
	 편지내용을 새로 삽입한다.
	 */
	int insertLetter(LetterDTO ldto);
	
	/**
	 해당유저가 가장 최근에 삽입한 letterNo를 가져온다.
	*/
	String selectLastInsertKey(LetterDTO dto);
	/**
	 새로 삽입된 letterNo를 바탕으로 생성 URL해준다.
	 */
	int updateURL(LetterDTO ldto);

}
