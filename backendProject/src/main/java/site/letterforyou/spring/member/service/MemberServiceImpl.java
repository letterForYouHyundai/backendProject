package site.letterforyou.spring.member.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import site.letterforyou.spring.member.domain.MemberDTO;
import site.letterforyou.spring.member.mapper.MemberMapper;

@Log4j
@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberMapper memberMapper;

	@Override
	public int selectMemberCnt(MemberDTO mvo) {
		
		int cnt = memberMapper.selectMemberCnt(mvo);
		
		return cnt;
	}

	@Override
	public MemberDTO getKaKaoAccessAndRefreshToken(String code) {
		
		MemberDTO mdto = new MemberDTO();
		
		String access_token="";
		String refresh_token="";
		
		//oauth token 요청 URL
		String requestURL ="https://kauth.kakao.com/oauth/token";
		
        try {
        	
        	URL url = new URL(requestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=ad117e5251ddb446c15d829ce0967079"); //REST_API_KEY 
            
            //이후에 프론트 localhost:3000과 라우팅 주소로 변경
            sb.append("&redirect_uri=http://localhost:8081/api/member/kakaoLoginPage"); //인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            
            int responseCode = conn.getResponseCode();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            
           log.info("result 응답코드 : "+ result);
           
           /**result에서 access_token과 refresh_token가져오기*/
           ObjectMapper mapper = new ObjectMapper();
           Map<String, String> returnMap = mapper.readValue(result.toString(), Map.class);
           
           access_token = returnMap.get("access_token");
           refresh_token = returnMap.get("refresh_token");
            
            log.info("oauth 응답코드 : "+ responseCode);
            MemberDTO paramDto = getKaKaoUserInfo(access_token);
            
            
            //refresh 토큰 value를 insert
            mdto.setUserId(paramDto.getUserId());
            mdto.setUserEmail(paramDto.getUserEmail());
            mdto.setUserName(paramDto.getUserName());
            mdto.setUserNickname(paramDto.getUserNickname());
            mdto.setAccessToken(access_token);
            mdto.setRefreshToken(refresh_token);
            memberMapper. insertRefreshToken(mdto);
            
		}catch (IOException e) {
			log.error("oauth token 발급 요청 ing 중 에러"+e.getMessage());
		}
		
		return mdto;
	}

	@Override
	public MemberDTO getKaKaoUserInfo(String refreshToken) {
		
		String requestURL = "https://kapi.kakao.com/v2/user/me";
		
		MemberDTO mdto = null;
		try {
			 URL url;
			 url = new URL(requestURL);
			 HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		    conn.setRequestMethod("POST");
		    conn.setDoOutput(true);
		    conn.setRequestProperty("Authorization",   "Bearer "+refreshToken); 

		    int responseCode = conn.getResponseCode();
		   
		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
               result.append(line);
            }
           
           
            /**result에서 사용자 정보 가져오기*/
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> returnMap = mapper.readValue(result.toString(), Map.class);
            
            
            /**사용자 정보를 member테이블에 저장한다.*/
            mdto =  insertMemberInfo(returnMap);
       
           
		} catch (Exception e) {
			log.error("유저 정보 조회 요청 중 에러"+e.getMessage());
		}
	      

		return mdto;
	}

	@Override
	public MemberDTO insertMemberInfo(Map<String, Object> map) {
		
		 MemberDTO mdto = new MemberDTO();
	     Map<String, Object> kakao_account = (Map<String, Object>) map.get("kakao_account");
     
         Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
       
         String email= String.valueOf(kakao_account.get("email"));
         String name= String.valueOf(profile.get("nickname"));
         String userImage= String.valueOf(profile.get("profile_image_url"));
                  
         /**
          	초기에는 User의 name과 nickname을 모두 카카오 계정에서 가져오는 프로필의 nickname 정보로 설정한다.
          	마이페이지에서 수정은 userName은 불가, userNickName만 수정할 수 있어야한다.
          **/
         mdto.setUserEmail(email);
         mdto.setUserName(name);
         mdto.setUserNickname(name);
         mdto.setUserImage(userImage);
         mdto.setChkMemberEmail("Y");
         
         int chkCnt = memberMapper.selectMemberCnt(mdto);
         
         log.info("chkCnt: "+ chkCnt);
         
         /**
          해당 카카오 계정으로 회원가입을 한 적이 없는 경우 TBL_USER 테이블에 정보를 삽입한다.
          */
         if( chkCnt == 0) {
        	 memberMapper.insertMemberInfo(mdto);
         }
         
         MemberDTO returnDto = memberMapper.selectMemberInfo(mdto);
         
         return returnDto;
         
	}

}
