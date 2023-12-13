package site.letterforyou.spring.member.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.ResponseUtil;
import site.letterforyou.spring.member.domain.MemberDTO;
import site.letterforyou.spring.member.mapper.MemberMapper;

@Log4j
@Service
@PropertySource("classpath:application.properties")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Value("${kakao.client.id}")
	String client_id;
	
	@Value("${kakao.redirect_uri}")
	String redirect_uri;
	

    @Autowired
	ResponseUtil responseUtil;
	

	@Override
	public int selectMemberCnt(MemberDTO mvo) {
		
		int cnt = memberMapper.selectMemberCnt(mvo);
		
		return cnt;
	}

	@Override
	public ResponseSuccessDTO<MemberDTO> getKaKaoAccessAndRefreshToken(String code, HttpSession session) {
		log.info(redirect_uri+" "+ client_id);
		MemberDTO result = new MemberDTO();
		
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
            sb.append("&client_id="+client_id); //REST_API_KEY 
            
            //이후에 프론트 localhost:3000과 라우팅 주소로 변경
            sb.append("&redirect_uri="+redirect_uri); //인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            
            int responseCode = conn.getResponseCode();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder apiResult = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
            	apiResult.append(line);
            }
            
           log.info("result 응답코드 : "+ responseCode);
           
           /**result에서 access_token과 refresh_token가져오기*/
           ObjectMapper mapper = new ObjectMapper();
           Map<String, String> returnMap = mapper.readValue(apiResult.toString(), Map.class);
           
           access_token = returnMap.get("access_token");
           refresh_token = returnMap.get("refresh_token");
            
            log.info("oauth 응답코드 : "+ responseCode);
            MemberDTO paramDto = getKaKaoUserInfo(access_token);
            
            
            //refresh 토큰 value를 insert
            result.setUserId(paramDto.getUserId());
            result.setUserEmail(paramDto.getUserEmail());
            result.setUserName(paramDto.getUserName());
            result.setUserNickname(paramDto.getUserNickname());
            result.setAccessToken(access_token);
            result.setRefreshToken(refresh_token);
            
            
            memberMapper. insertRefreshToken(result);
            
            if (result.getAccessToken() != null && !result.getAccessToken().isEmpty()) {
                session.setAttribute("userInfo", result);
            }
            
		}catch (IOException e) {
			log.error("oauth token 발급 요청 ing 중 에러"+e.getMessage());
			return  responseUtil.successResponse(result, HttpStatus.UNAUTHORIZED);
		}
       
        return  responseUtil.successResponse(result, HttpStatus.OK);
        
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

	@Override
	public ResponseSuccessDTO<MemberDTO> kakaoLogout(HttpSession session) {
		 MemberDTO mdto = (MemberDTO)session.getAttribute("userInfo");
		 
		String access_token = mdto.getAccessToken();
		log.info("access_token: "+access_token);
		String requestURL ="https://kapi.kakao.com/v1/user/logout";
		try {
		URL url;
		 url = new URL(requestURL);
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		 conn.setRequestMethod("POST");
		 conn.setDoOutput(true);
		 conn.setRequestProperty("Authorization",   "Bearer "+access_token); 
		 
		  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
          String line = "";
          StringBuilder apiResult = new StringBuilder();

          while ((line = bufferedReader.readLine()) != null) {
        	  apiResult.append(line);
          }
          
          session.removeAttribute("userInfo"); 
		}catch(Exception e) {
			log.error("로그아웃 처리중 에러: "+e.getMessage());
			return  responseUtil.successResponse(mdto, HttpStatus.UNAUTHORIZED);
		}
		
		return  responseUtil.successResponse(mdto, HttpStatus.OK);
	}

	@Override
	public ResponseSuccessDTO<MemberDTO> checkMemberYn(String userEmail) {

		MemberDTO mdto = new MemberDTO();
		mdto.setUserEmail(userEmail);
		log.info("userEmail: "+userEmail);
		
		int checkMember = memberMapper.selectMemberCnt(mdto);
		
		//회원인 경우 멤버여부를 Y로 셋팅해 준다.
		if(checkMember > 0) {
			mdto.setCheckMemberYn("Y");
		}else {
			mdto.setCheckMemberYn("N");
		}
		
		return responseUtil.successResponse(mdto, HttpStatus.OK);
	}

}
