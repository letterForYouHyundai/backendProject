package site.letterforyou.spring.letter.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.letterforyou.spring.letter.domain.LetterVO;
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


	public List<LetterVO> getReceivedLetters(@Param("userId")String userId , @Param("offset")Long offset, @Param("size")Long size);
	
	public List<LetterVO> getSendLetters(@Param("userId")String userId , @Param("offset")Long offset, @Param("size")Long size);
	
	public LetterVO getReceivedLetter(@Param("letterNo") Long letterNo);
	
	public LetterVO getSendLetter(@Param("letterNo") Long letterNo); 
	public void deleteReceivedLetter(Long letterNo);
	public void deleteSendLetter(Long letterNo);
	public int getTotalCountReceivedLetterByUserId(String userId);
	public int getTotalCountSendLetterByUserId(String userId);



}
