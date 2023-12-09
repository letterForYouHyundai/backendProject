package site.letterforyou.spring.letter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import site.letterforyou.spring.common.domain.PageVO;
import site.letterforyou.spring.common.domain.Pagination;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.ResponseUtil;
import site.letterforyou.spring.common.util.TimeService;
import site.letterforyou.spring.letter.domain.LetterVO;
import site.letterforyou.spring.letter.dto.LetterDTO;
import site.letterforyou.spring.letter.dto.LetterDeleteLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetListResponseDTO;
import site.letterforyou.spring.letter.mapper.LetterMapper;

@Service
@Slf4j
public class LetterServiceImpl implements LetterService {

	@Autowired
	ResponseUtil responseUtil;

	@Autowired
	LetterMapper letterMapper;
	
	@Autowired
	TimeService timeService;

	@Override
	public ResponseSuccessDTO<LetterGetListResponseDTO> getLetterReceiveList(Long page, String userId) {
		LetterGetListResponseDTO result = new LetterGetListResponseDTO();
		PageVO pageVo = new PageVO(page, 10L, 10L);
		Long offset = pageVo.getOffset();
		Long size = pageVo.getRecordSize();
		log.info(page+" "+ userId+" " +offset+" "+size);
		List<LetterVO> letterVoList = letterMapper.getReceivedLetters(userId, offset, size);

		List<Long> letterList = new ArrayList<>();
		for (LetterVO l : letterVoList) {
			

			letterList.add(l.getLetterNo());
		}
		
		int count = letterMapper.getTotalCountReceivedLetterByUserId(userId);
		Pagination pagination = new Pagination(count, pageVo);
		
		result.setLetterList(letterList);
		result.setPagination(pagination);
		ResponseSuccessDTO<LetterGetListResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}
	
	@Override
	public ResponseSuccessDTO<LetterGetLetterResponseDTO> getReceivedLetter(Long letterNo) {
		LetterVO letterVo = letterMapper.getReceivedLetter(letterNo);
		LetterGetLetterResponseDTO result = new LetterGetLetterResponseDTO();
		LetterDTO letterDTO = new LetterDTO();
		letterDTO.setLetterNo(letterVo.getLetterNo());
		letterDTO.setLetterTitle(letterVo.getLetterTitle());
		letterDTO.setLetterContent(letterVo.getLetterContent());
		letterDTO.setLetterColorNo(letterVo.getLetterColorNo());
		letterDTO.setReceiverNickname(letterVo.getReceiverNickname());
		letterDTO.setSenderNickname(letterVo.getSenderNickname());
		letterDTO.setRegistDate(timeService.parseLocalDateTimeForLetter(letterVo.getRegistDate()));
		result.setLetterDTO(letterDTO);
		ResponseSuccessDTO<LetterGetLetterResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}
	
	@Override
	public ResponseSuccessDTO<LetterDeleteLetterResponseDTO> deleteReceivedLetter(Long letterNo) {
		letterMapper.deleteReceivedLetter(letterNo);
		ResponseSuccessDTO<LetterDeleteLetterResponseDTO> res = responseUtil.successResponse("받은 편지"+  letterNo+ " 번이 삭제되었습니다.", HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<LetterGetListResponseDTO> getLetterSendList(Long page, String userId) {
		LetterGetListResponseDTO result = new LetterGetListResponseDTO();
		PageVO pageVo = new PageVO(page, 10L, 10L);
		Long offset = pageVo.getOffset();
		Long size = pageVo.getRecordSize();
		log.info(page+" "+ userId+" " +offset+" "+size);
		List<LetterVO> letterVoList = letterMapper.getSendLetters(userId, offset, size);

		List<Long> letterList = new ArrayList<>();
		for (LetterVO l : letterVoList) {
			

			letterList.add(l.getLetterNo());
		}
		
		int count = letterMapper.getTotalCountSendLetterByUserId(userId);
		Pagination pagination = new Pagination(count, pageVo);
		
		result.setLetterList(letterList);
		result.setPagination(pagination);
		ResponseSuccessDTO<LetterGetListResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<LetterGetLetterResponseDTO> getSendLetter(Long letterNo) {
		LetterVO letterVo = letterMapper.getSendLetter(letterNo);
		LetterGetLetterResponseDTO result = new LetterGetLetterResponseDTO();
		LetterDTO letterDTO = new LetterDTO();
		letterDTO.setLetterNo(letterVo.getLetterNo());
		letterDTO.setLetterTitle(letterVo.getLetterTitle());
		letterDTO.setLetterContent(letterVo.getLetterContent());
		letterDTO.setLetterColorNo(letterVo.getLetterColorNo());
		letterDTO.setReceiverNickname(letterVo.getReceiverNickname());
		letterDTO.setSenderNickname(letterVo.getSenderNickname());
		letterDTO.setRegistDate(timeService.parseLocalDateTimeForLetter(letterVo.getRegistDate()));
		result.setLetterDTO(letterDTO);
		ResponseSuccessDTO<LetterGetLetterResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<LetterDeleteLetterResponseDTO> deleteSendLetter(Long letterNo) {
		letterMapper.deleteSendLetter(letterNo);
		ResponseSuccessDTO<LetterDeleteLetterResponseDTO> res = responseUtil.successResponse("보낸 편지"+  letterNo+ " 번이 삭제되었습니다.", HttpStatus.OK);

		return res;
	}

	

}
