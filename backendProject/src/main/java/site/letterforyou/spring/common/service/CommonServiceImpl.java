package site.letterforyou.spring.common.service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import site.letterforyou.spring.common.dto.ResponseSuccessDTO;
import site.letterforyou.spring.common.util.TimeService;
import site.letterforyou.spring.member.domain.MemberDTO;

@Service
public class CommonServiceImpl implements CommonService {

    @Value("${encrypt.receive.algorithm}")
    private String RECEIVE_ALGORITHM;
    @Value("${encrypt.receive.secret}")
    private String RECEIVE_SECRET_KEY; 
    
    @Value("${encrypt.send.algorithm}")
    private String SEND_ALGORITHM;
    
    @Value("${encrypt.send.secret}")
    private String SEND_SECRET_KEY;

    @Autowired
	private TimeService timeService;
    
    public String encryptReceive(String plainText) throws Exception {
        Key key = generateKeyForReceive(RECEIVE_SECRET_KEY, RECEIVE_ALGORITHM);
        Cipher cipher = Cipher.getInstance(RECEIVE_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getUrlEncoder().encodeToString(encryptedBytes);
    }

    public Long decryptReceive(String encryptedText) throws Exception {
        try {
            Key key = generateKeyForReceive(RECEIVE_SECRET_KEY, RECEIVE_ALGORITHM);
            Cipher cipher = Cipher.getInstance(RECEIVE_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedText));
            String decryptedString = new String(decryptedBytes);
            return Long.parseLong(decryptedString);
        } catch (NumberFormatException e) {
            throw new Exception(" String -> Long Exception" + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Decryption failed: " + e.getMessage());
        }
    }
    
    public String encryptSend(String plainText) throws Exception {
        Key key = generateKeyForReceive(SEND_SECRET_KEY, SEND_ALGORITHM);
        Cipher cipher = Cipher.getInstance(SEND_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getUrlEncoder().encodeToString(encryptedBytes);
    }

    public Long decryptSend(String encryptedText) throws Exception {
        try {
            Key key = generateKeyForReceive(SEND_SECRET_KEY, SEND_ALGORITHM);
            Cipher cipher = Cipher.getInstance(SEND_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(encryptedText));
            String decryptedString = new String(decryptedBytes);
            return Long.parseLong(decryptedString);
        } catch (NumberFormatException e) {
            throw new Exception(" String -> Long Exception" + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Decryption failed: " + e.getMessage());
        }
    }
    private static Key generateKeyForReceive(String secretKey, String algorithm) {
        return new SecretKeySpec(secretKey.getBytes(), algorithm);
    }
    private static Key generateKeyForSend(String secretKey, String algorithm) {
    	byte[] keyBytes = new byte[16];
    	System.arraycopy(secretKey.getBytes(), 0, keyBytes, 0, Math.min(secretKey.getBytes().length, keyBytes.length));
        return new SecretKeySpec(keyBytes, algorithm);
    }

    @Override
    public ResponseSuccessDTO<MemberDTO> insertLetter(HttpSession session) {
        MemberDTO userDTO = (MemberDTO) session.getAttribute("userInfo");

        ResponseSuccessDTO<MemberDTO> responseDTO = ResponseSuccessDTO
                .<MemberDTO>builder()
                .timeStamp(timeService.parseLocalDateTime(LocalDateTime.now()))
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .result(userDTO)
                .build();
        
        return responseDTO;
    }

    
}

