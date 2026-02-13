package com.tecbom.e_commerce.Infra.Security.Service;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProcessingErrorException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptographyService {

    // Em um projeto real a chave jamais poderia ficar aqui, normalmente se usa uma variavel de ambiente
    private static final String key = "ChaveSecreta_123";

    private static final String ALGORITHM = "AES";

    public static String encrypt(String plaintext) throws ProcessingErrorException {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encrypted = cipher.doFinal(plaintext.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new ProcessingErrorException();
        }
    }

    public static String decrypt(String ciphertext) throws ProcessingErrorException {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(ciphertext));

            return new String(decrypted);

        } catch (Exception e) {
            throw new ProcessingErrorException();
        }
    }
}
