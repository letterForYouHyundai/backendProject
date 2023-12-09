package site.letterforyou.spring.letter.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.letterforyou.spring.letter.domain.LetterVO;

public interface LetterMapper {

	public List<LetterVO> getReceivedLetters(@Param("userId")String userId , @Param("offset")Long offset, @Param("size")Long size);
	
	public List<LetterVO> getSendLetters(@Param("userId")String userId , @Param("offset")Long offset, @Param("size")Long size);
	
	public LetterVO getReceivedLetter(@Param("letterNo") Long letterNo);
	
	public LetterVO getSendLetter(@Param("letterNo") Long letterNo); 
	public void deleteReceivedLetter(Long letterNo);
	public void deleteSendLetter(Long letterNo);
	public int getTotalCountReceivedLetterByUserId(String userId);
	public int getTotalCountSendLetterByUserId(String userId);


}
