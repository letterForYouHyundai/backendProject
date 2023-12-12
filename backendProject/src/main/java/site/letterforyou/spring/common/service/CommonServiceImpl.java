package site.letterforyou.spring.common.service;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;

@Service
public class CommonServiceImpl implements CommonService {
	
	private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "jwybtZEALFFmheND"; // 반드시 16, 24 또는 32바이트여야 함

    public String encrypt(String plainText) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        try {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new Exception("Decryption failed: " + e.getMessage());
        }
    }

    private static Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    }
}
