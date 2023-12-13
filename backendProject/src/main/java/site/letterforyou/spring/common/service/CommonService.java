package site.letterforyou.spring.common.service;

public interface CommonService {
	
	 /**
	  Receive key를 암호화 한다.
	 */
	  public  String encryptReceive(String plainText)  throws Exception ;
	  /**
	  Receive key를 복호화 한다.
	 */
	  public  Long decryptReceive(String encryptedText)  throws Exception ;	
	  
	  
	  /**
	  Send key를 암호화 한다.
	 */
	  public  String encryptSend(String plainText)  throws Exception ;
	  /**
	  Send key를 복호화 한다.
	 */
	  public  Long decryptSend(String encryptedText)  throws Exception ;	 
}
