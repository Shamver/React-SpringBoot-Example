package kr.co.upcoding.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;

//고급 암호화 표준(AES, Advanced Encryption Standard)
//암호화와 복호화 과정에서 동일한 키를 사용하는 대칭 키 알고리즘
public class AES256Cipher {

    private static volatile AES256Cipher INSTANCE;

    final static String secretKey   = System.getenv("AES256_SECRET_KEY"); //32bit
    static String IV  = ""; //16bit

    public static AES256Cipher getInstance(){
        if(INSTANCE==null){
            synchronized(AES256Cipher.class){
                if(INSTANCE==null)
                    INSTANCE=new AES256Cipher();
            }
        }
        return INSTANCE;
    }

    private AES256Cipher(){
        IV = secretKey.substring(0,16);
    }

    //암호화
    public static String AES_Encode(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        byte[] keyData = secretKey.getBytes();

        SecretKey secureKey = new SecretKeySpec(keyData, "AES");

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));

        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        String enStr = new String(Base64.encodeBase64(encrypted));

        return enStr;
    }

    //복호화
    public static String AES_Decode(String str) throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        byte[] keyData = secretKey.getBytes();
        SecretKey secureKey = new SecretKeySpec(keyData, "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));

        byte[] byteStr = Base64.decodeBase64(str.getBytes());

        return new String(c.doFinal(byteStr),"UTF-8");
    }

    //키생서
    public static byte[] generationAES256_KEY() throws NoSuchAlgorithmException{
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(256);
        SecretKey key = kgen.generateKey();

        return key.getEncoded();

    }
}