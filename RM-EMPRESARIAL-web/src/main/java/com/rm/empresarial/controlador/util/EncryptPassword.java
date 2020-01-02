/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import java.io.Serializable;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Marco
 */
@Named(value = "encryptPassword")
@ViewScoped
public class EncryptPassword implements Serializable{
        
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
        Cipher cipher = Cipher.getInstance(EncryptPassword.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        return encryptedValue64;
               
    }
    
    public  String decrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(EncryptPassword.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;
                
    }
    
    private  Key generateKey() throws Exception 
    {
        Key key = new SecretKeySpec(EncryptPassword.KEY.getBytes(),EncryptPassword.ALGORITHM);
        return key;
    }
    
     public  String encrypt2() throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(EncryptPassword.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(passwordOriginal.getBytes("utf-8"));
        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        return encryptedValue64;
               
    }
    
    public  String decrypt2() throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(EncryptPassword.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(passwordEncriptada);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;
                
    }
    
}
