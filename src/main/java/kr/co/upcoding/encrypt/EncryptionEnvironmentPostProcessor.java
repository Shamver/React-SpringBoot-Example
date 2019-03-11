package kr.co.upcoding.encrypt;

import org.apache.tomcat.jni.OS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import kr.co.upcoding.encrypt.AES256Cipher;
import java.util.Properties;

public class EncryptionEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties props = new Properties();
        try {
            // 암호화 한 문자열 보기 ( datasource의 정보를 하단 인자에 넣어주세요.)
            //System.out.println("encrypted spring.datasource.password : "+AES256Cipher.getInstance().AES_Encode("username"));
            //System.out.println("encrypted spring.datasource.username : "+AES256Cipher.getInstance().AES_Encode("password"));
            //System.out.println("encrypted spring.datasource.url : "+AES256Cipher.getInstance().AES_Encode("url"));
            props.put("spring.datasource.password", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.password")));
            props.put("spring.datasource.username", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.username")));
            props.put("spring.datasource.url", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.url")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    }



}
