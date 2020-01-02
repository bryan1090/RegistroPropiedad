/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloRest;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.Setter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author JeanCarlos
 */
public class encryptedPassword {
    
    @Getter
    @Setter
    private String passwordOriginal;
    
    @Getter
    @Setter
    private String passwordEncriptada;
    
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
   
   
    public  String encrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        return encryptedValue64;
               
    }
    
    public  String decrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;
                
    }
    
    private  Key generateKey() throws Exception 
    {
        Key key = new SecretKeySpec(KEY.getBytes(),ALGORITHM);
        return key;
    }
    
}