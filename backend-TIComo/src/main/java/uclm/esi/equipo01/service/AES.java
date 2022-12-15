package uclm.esi.equipo01.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    private AES(){
      //Construcctor vacio
    }
    
    public static void setKey(final String myKey) {
        MessageDigest sha = null;
        try {
          key = myKey.getBytes("UTF_8");
          sha = MessageDigest.getInstance("SHA-1");
          key = sha.digest(key);
          key = Arrays.copyOf(key, 16);
          secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }

      public static String encrypt(final String strToEncrypt, final String secret) {
        try {
          setKey(secret);
          Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
          cipher.init(Cipher.ENCRYPT_MODE, secretKey);
          return Base64.getEncoder()
            .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF_8")));
        } catch (Exception e) {
          return e.getMessage();
        }
        
      }
    
      public static String decrypt(final String strToDecrypt, final String secret) {
        try {
          setKey(secret);
          Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
          cipher.init(Cipher.DECRYPT_MODE, secretKey);
          return new String(cipher.doFinal(Base64.getDecoder()
            .decode(strToDecrypt)));
        } catch (Exception e) {
          return e.getMessage();
        }
        
      }
}
