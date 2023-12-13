package site.letterforyou.spring.letter.service;

import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.letter.dto.LetterDeleteLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetReceiveListResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetSendListResponseDTO;

public interface LetterService {

	// String insertLetter(LetterDTO ldto);
	ResponseSuccessDTO<LetterDTO> insertLetter(LetterDTO ldto) throws Exception;
	
	String sendKaoKaoMessage(LetterDTO ldto);
	
	String makeURL(LetterDTO ldto);

	public ResponseSuccessDTO<LetterGetReceiveListResponseDTO> getLetterReceiveList(Long page, String userId);	
	public ResponseSuccessDTO<LetterGetSendListResponseDTO> getLetterSendList(Long page, String userId);
	
	public ResponseSuccessDTO<LetterGetLetterResponseDTO> getReceivedLetter(String letterNo);
	public ResponseSuccessDTO<LetterGetLetterResponseDTO> getSendLetter(String letterNo);
	public ResponseSuccessDTO<LetterDeleteLetterResponseDTO>  deleteReceivedLetter(String letterNo);
	public ResponseSuccessDTO<LetterDeleteLetterResponseDTO> deleteSendLetter(String letterNo);


}
