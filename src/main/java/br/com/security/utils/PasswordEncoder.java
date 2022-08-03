package br.com.security.utils;

import jakarta.inject.Singleton;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

@Singleton
public class PasswordEncoder {
    private static final String SALT = "JAxLWp0Bxkw2XyffFAHV";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";


    public  String encodePassword(String password) {
        byte[] securePassword = hash(password.toCharArray(), SALT.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    public  boolean validatePassword(String password,
                                             String encodedPassword)
    {
        return encodePassword(password).equalsIgnoreCase(encodedPassword);
    }

    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}