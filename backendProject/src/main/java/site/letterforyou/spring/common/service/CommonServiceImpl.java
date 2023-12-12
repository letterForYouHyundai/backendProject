package site.letterforyou.spring.common.service;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Value("${encrypt.algorithm}")
    private String ALGORITHM;
    @Value("${encrypt.secret}")
    private String SECRET_KEY; // Must be 16, 24, or 32 bytes

    public String encrypt(String plainText) throws Exception {
        Key key = generateKey(SECRET_KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getUrlEncoder().encodeToString(encryptedBytes);
    }

    public Long decrypt(String encryptedText) throws Exception {
        try {
            Key key = generateKey(SECRET_KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
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

    private static Key generateKey(String secretKey, String algorithm) {
        return new SecretKeySpec(secretKey.getBytes(), algorithm);
    }
}

