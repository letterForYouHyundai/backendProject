package site.letterforyou.spring.letter.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import site.letterforyou.spring.common.domain.PageVO;
import site.letterforyou.spring.common.domain.Pagination;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.service.CommonService;
import site.letterforyou.spring.common.util.ResponseUtil;
import site.letterforyou.spring.common.util.TimeService;
import site.letterforyou.spring.exception.service.DecryptionFailedException;
import site.letterforyou.spring.exception.service.DefaultException;
import site.letterforyou.spring.exception.service.EntityNullException;
import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.letter.domain.LetterVO;
import site.letterforyou.spring.letter.dto.LetterDeleteLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetLetterResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetReceiveListResponseDTO;
import site.letterforyou.spring.letter.dto.LetterGetSendListResponseDTO;
import site.letterforyou.spring.letter.dto.LetterReceiveDTO;
import site.letterforyou.spring.letter.dto.LetterSendDTO;
import site.letterforyou.spring.letter.dto.Letterdtos;
import site.letterforyou.spring.letter.mapper.LetterMapper;
import site.letterforyou.spring.member.domain.MemberDTO;
import site.letterforyou.spring.member.mapper.MemberMapper;

@Service
@Log4j
public class LetterServiceImpl implements LetterService {

	@Autowired
	ResponseUtil responseUtil;

	@Autowired
	TimeService timeService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private LetterMapper letterMapper;

	@Value("${letter4u.url}")
	private String contextUrl;

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public ResponseSuccessDTO<LetterDTO> insertLetter(LetterDTO result) {

		String url = "";

		log.info("result확인 : " + result.toString());

		// 체크박스 여부가 Y이고, 회원 이메일이 존재하는 경우
		if ("Y".equals(result.getCheckYn()) && result.getLetterReceiveId() != null
				&& !result.getLetterReceiveId().isEmpty() && !("".equals(result.getLetterReceiveId()))) {
			MemberDTO mdto = new MemberDTO();
			mdto.setUserEmail(result.getLetterReceiveId());
			MemberDTO resultMdto = memberMapper.selectMemberInfo(mdto);
			result.setLetterReceiveId(String.valueOf(resultMdto.getUserId()));
		}

		letterMapper.insertLetter(result);

		String letterNo = letterMapper.selectLastInsertKey(result);
		String encryptNo = "";
	
		try {
			encryptNo = commonService.encryptReceive(letterNo);
		} catch (Exception e) {
			log.info("암호화 중 오류 발생" + e.getMessage());
		}
		String URL = contextUrl + "/letter/receive/" + encryptNo;
		
		result.setLetterUrl(URL);
		result.setLetterNo(letterNo);
		letterMapper.updateURL(result);

		return responseUtil.successResponse(result, HttpStatus.OK);

	}

	@Override
	public String sendKaoKaoMessage(LetterDTO ldto) {

		String requestURL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

		try {
			URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// Access Token 설정
			conn.setRequestProperty("Authorization", "Bearer " + ldto.getAccessToken());

			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// 메시지 내용 및 링크 수정
			JSONObject linkObj = new JSONObject();
			linkObj.put("web_url", "https://developers.kakao.com");
			linkObj.put("mobile_web_url", "https://developers.kakao.com");
			linkObj.put("android_execution_params", "contentId=100");
			linkObj.put("ios_execution_params", "contentId=100");

			JSONObject templateObj = new JSONObject();
			templateObj.put("object_type", "text");
			templateObj.put("content", new JSONObject().put("title", ldto.getLetterTitle())
					.put("description", ldto.getLetterContent()).put("link", linkObj));

			String messageData = "template_object=" + templateObj.toString();

			DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
			outputStream.writeBytes(messageData);
			outputStream.flush();
			outputStream.close();

			int responseCode = conn.getResponseCode();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder result = new StringBuilder();

			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> returnMap = mapper.readValue(result.toString(), Map.class);

		} catch (Exception e) {
			log.error("카카오 API 메시지 전송 요청 중 에러: " + e.getMessage());
		}
		return null;
	}

	@Override
	public String makeURL(LetterDTO ldto) {

		return null;
	}

	@Override
	public ResponseSuccessDTO<LetterGetReceiveListResponseDTO> getLetterReceiveList(Long page, String userId) {
		LetterGetReceiveListResponseDTO result = new LetterGetReceiveListResponseDTO();
		PageVO pageVo = new PageVO(page, 10L, 10L);
		Long offset = pageVo.getOffset();
		Long size = pageVo.getRecordSize();
		List<LetterVO> letterVoList = letterMapper.getReceivedLetters(userId, offset, size);
		
		List<LetterReceiveDTO> letterList = new ArrayList<>();
		for (LetterVO l : letterVoList) {
			LetterReceiveDTO letterDTO = new LetterReceiveDTO();

			try {
				letterDTO.setLetterNo(commonService.encryptReceive(l.getLetterNo() + ""));
			} catch (Exception e) {
				throw new DefaultException("암호화 중 오류 발생");
			}
			letterDTO.setColorPalette(letterMapper.getLetterColor(l.getLetterColorNo()));
			letterDTO.setLetterReceiveYn(l.getLetterReceiveYn());
			letterDTO.setSenderNickname(l.getSenderNickname());
			letterList.add(letterDTO);
		}

		int count = letterMapper.getTotalCountReceivedLetterByUserId(userId);
		Pagination pagination = new Pagination(count, pageVo);

		result.setLetterList(letterList);
		result.setPagination(pagination);
		ResponseSuccessDTO<LetterGetReceiveListResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<LetterGetLetterResponseDTO> getReceivedLetter(String letterNo) {
		Long decryptNo;
		try {
			decryptNo = commonService.decryptReceive(letterNo);
		} catch (Exception e) {
			throw new DecryptionFailedException("접근할 수 없는 페이지 입니다.");
		}
		LetterVO letterVo = letterMapper.getReceivedLetter(decryptNo);
		if (letterVo == null) {

			throw new EntityNullException("받은 편지가 존재하지 않습니다.");
		}
		letterMapper.updateLetterRecieve(decryptNo);

		LetterGetLetterResponseDTO result = new LetterGetLetterResponseDTO();
		Letterdtos letterDTO = new Letterdtos();
		try {
			letterDTO.setLetterNo(commonService.encryptReceive(letterVo.getLetterNo() + ""));
		} catch (Exception e) {
			throw new DefaultException("암호화 중 오류 발생");
		}
		letterDTO.setLetterTitle(letterVo.getLetterTitle());
		letterDTO.setLetterContent(letterVo.getLetterContent());
		letterDTO.setColorPalette(letterMapper.getLetterColor(letterVo.getLetterColorNo()));
		letterDTO.setReceiverNickname(letterVo.getReceiverNickname());
		letterDTO.setSenderNickname(letterVo.getSenderNickname());
		letterDTO.setRegistDate(timeService.parseLocalDateTimeForLetter(letterVo.getRegistDate()));
		letterDTO.setUserAlias(letterVo.getUserAlias());
		letterDTO.setLetterReceiveYn(letterVo.getLetterReceiveYn());
		result.setLetterDTO(letterDTO);
		ResponseSuccessDTO<LetterGetLetterResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<LetterDeleteLetterResponseDTO> deleteReceivedLetter(String letterNo) {
		Long decryptNo;
		try {
			decryptNo = commonService.decryptReceive(letterNo);
		} catch (Exception e) {
			throw new DefaultException(e.getMessage());
		}
		letterMapper.deleteReceivedLetter(decryptNo);
		ResponseSuccessDTO<LetterDeleteLetterResponseDTO> res = responseUtil
				.successResponse("받은 편지" + letterNo + " 번이 삭제되었습니다.", HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<LetterGetSendListResponseDTO> getLetterSendList(Long page, String userId) {
		LetterGetSendListResponseDTO result = new LetterGetSendListResponseDTO();
		PageVO pageVo = new PageVO(page, 10L, 10L);
		Long offset = pageVo.getOffset();
		Long size = pageVo.getRecordSize();
		List<LetterVO> letterVoList = letterMapper.getSendLetters(userId, offset, size);

		List<LetterSendDTO> letterList = new ArrayList<>();
		for (LetterVO l : letterVoList) {
			LetterSendDTO letterDTO = new LetterSendDTO();
			try {
				letterDTO.setLetterNo(commonService.encryptSend(l.getLetterNo() + ""));
			} catch (Exception e) {
				throw new DefaultException("암호화 중 오류 발생");
			}
			letterDTO.setReceiverNickname(l.getUserAlias());
			letterDTO.setColorPalette(letterMapper.getLetterColor(l.getLetterColorNo()));
			letterDTO.setLetterReceiveYn(l.getLetterReceiveYn());
			letterList.add(letterDTO);
		}

		int count = letterMapper.getTotalCountSendLetterByUserId(userId);
		Pagination pagination = new Pagination(count, pageVo);

		result.setLetterList(letterList);
		result.setPagination(pagination);
		ResponseSuccessDTO<LetterGetSendListResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<LetterGetLetterResponseDTO> getSendLetter(String letterNo) {
		Long decryptNo;
		try {
			decryptNo = commonService.decryptSend(letterNo);
		} catch (Exception e) {
			throw new DecryptionFailedException("접근할 수 없는 페이지 입니다.");
		}
		LetterVO letterVo = letterMapper.getSendLetter(decryptNo);

		if (letterVo == null) {
			throw new EntityNullException("받은 편지가 존재하지 않습니다.");
		}
		LetterGetLetterResponseDTO result = new LetterGetLetterResponseDTO();
		Letterdtos letterDTO = new Letterdtos();
		try {
			letterDTO.setLetterNo(commonService.encryptSend(letterVo.getLetterNo() + ""));
		} catch (Exception e) {
			throw new DefaultException("암호화 중 오류 발생");
		}
		letterDTO.setLetterTitle(letterVo.getLetterTitle());
		letterDTO.setLetterContent(letterVo.getLetterContent());
		letterDTO.setColorPalette(letterMapper.getLetterColor(letterVo.getLetterColorNo()));
		letterDTO.setReceiverNickname(letterVo.getReceiverNickname());
		letterDTO.setSenderNickname(letterVo.getSenderNickname());
		letterDTO.setRegistDate(timeService.parseLocalDateTimeForLetter(letterVo.getRegistDate()));
		letterDTO.setLetterReceiveYn(letterVo.getLetterReceiveYn());
		letterDTO.setUserAlias(letterVo.getUserAlias());
		result.setLetterDTO(letterDTO);
		ResponseSuccessDTO<LetterGetLetterResponseDTO> res = responseUtil.successResponse(result, HttpStatus.OK);

		return res;
	}

	@Override
	public ResponseSuccessDTO<LetterDeleteLetterResponseDTO> deleteSendLetter(String letterNo) {
		Long decryptNo;
		try {
			decryptNo = commonService.decryptSend(letterNo);
		} catch (Exception e) {
			throw new DefaultException(e.getMessage());
		}
		letterMapper.deleteSendLetter(decryptNo);
		ResponseSuccessDTO<LetterDeleteLetterResponseDTO> res = responseUtil
				.successResponse("보낸 편지" + letterNo + " 번이 삭제되었습니다.", HttpStatus.OK);

		return res;
	}

}
