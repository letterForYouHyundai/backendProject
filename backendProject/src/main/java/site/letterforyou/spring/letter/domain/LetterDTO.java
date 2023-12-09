package site.letterforyou.spring.letter.domain;

import lombok.Data;

@Data
public class LetterDTO {
	
	 /**
	   편지 번호
	  */
	  private String letterNo;
	  /**
	   편지 수신 아이디
	  */
	  private String letterReceiveId;
	  /**
	   편지 송신 아이디
	  */
	  private String letterSendId;
	  /**
	   편지 제목
	  */
	  private String letterTitle;
	  /**
	   편지 내용
	  */
	  private String letterContent;
	  
	  /**
	   편지 URL
	  */
	  private String letterUrl;
	  
	  /**
	   편지 작성일자
	  */
	  private String registDt;
	  
	  /**
	   편지 배경번호
	  */
	  private String letterColorNo;
	  
	  /**
	   받은 편지 열람여부
	  */
	  private String letterReceiveYn;
}