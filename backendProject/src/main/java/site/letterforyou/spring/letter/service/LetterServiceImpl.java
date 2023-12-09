package site.letterforyou.spring.letter.service;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import site.letterforyou.spring.letter.domain.LetterDTO;
import site.letterforyou.spring.letter.mapper.LetterMapper;

@Service
@Log4j
public class LetterServiceImpl implements LetterService {

	@Autowired
	private LetterMapper letterMapper;

	@Override
	public void insertLetter(LetterDTO ldto) {
		
		//ldto.setLetterReceiveId("user1");
		//ldto.setLetterSendId("user1");
		//ldto.setLetterTitle("title");
		//ldto.setLetterContent("content");
		//ldto.setLetterUrl("urlurl");
		//ldto.setKakaoSendYn("N");
		//ldto.setLetterColorNo("46");
		ldto.setLetterReceiveYn("2");
		
		letterMapper.insertLetter(ldto);
		log.info(ldto.toString());
		
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
	        templateObj.put("object_type", "feed");
	        templateObj.put("content", new JSONObject()
	            .put("title", "제목 디저트")
	            .put("description", "내용 디저트")
	            .put("image_url", "http://urltoimage.com/image.png")
	            .put("link", linkObj)
	        );

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

}
