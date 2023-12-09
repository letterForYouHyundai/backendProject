package site.letterforyou.spring.letter.service;

import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.letter.dto.LetterDeleteLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetListResponseDTO;

public interface LetterService {

public ResponseSuccessDTO<LetterGetListResponseDTO> getLetterReceiveList(Long page, String userId);	
public ResponseSuccessDTO<LetterGetListResponseDTO> getLetterSendList(Long page, String userId);
public ResponseSuccessDTO<LetterDeleteLetterResponseDTO>  deleteReceivedLetter(Long letterNo);

public ResponseSuccessDTO<LetterGetLetterResponseDTO> getReceivedLetter(Long letterNo);
public ResponseSuccessDTO<LetterGetLetterResponseDTO> getSendLetter(Long letterNo);
public ResponseSuccessDTO<LetterDeleteLetterResponseDTO> deleteSendLetter(Long letterNo);

}
