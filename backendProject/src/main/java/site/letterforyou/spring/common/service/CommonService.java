package site.letterforyou.spring.common.service;

public interface CommonService {
	
	 /**
	  key를 암호화 한다.
	 */
	  public  String encrypt(String plainText)  throws Exception ;
	  /**
	  key를 복호화 한다.
	 */
	  public  Long decrypt(String encryptedText)  throws Exception ;	 
}
