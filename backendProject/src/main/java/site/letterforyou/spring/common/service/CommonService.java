package site.letterforyou.spring.common.service;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public interface CommonService {
	
	 /**
	  key를 암호화 한다.
	 */
	  public  String encrypt(String plainText)  throws Exception ;
	  /**
	  key를 복호화 한다.
	 */
	  public  String decrypt(String encryptedText)  throws Exception ;	 
}
