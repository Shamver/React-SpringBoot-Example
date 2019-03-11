package kr.co.upcoding.encrypt;

import org.apache.tomcat.jni.OS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import kr.co.upcoding.encrypt.AES256Cipher;
import java.util.Properties;
import java.util.Scanner;

public class EncryptionEnvironmentPostProcessor implements EnvironmentPostProcessor {

    Scanner sc = new Scanner(System.in);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Properties props = new Properties();
        try {
            // 암호화 한 문자열 보기 ( datasource의 정보를 하단 인자에 넣어주세요.)
            if(!QuestionDataSourceEncryptedYN()) {

                System.out.print("암호화 할 username 을 입력해주세요 : ");
                String userName = sc.nextLine();
                System.out.print("암호화 할 password 을 입력해주세요 : ");
                String password = sc.nextLine();
                System.out.print("암호화 할 url 을 입력해주세요 : ");
                String url = sc.nextLine();
                System.out.println("encrypted spring.datasource.password : " + AES256Cipher.getInstance().AES_Encode(userName));
                System.out.println("encrypted spring.datasource.username : " + AES256Cipher.getInstance().AES_Encode(password));
                System.out.println("encrypted spring.datasource.url : " + AES256Cipher.getInstance().AES_Encode(url));

                System.out.println("해당 암호화된 정보들로 DataSource 수정 후 다시 서버를 재기동 해주시길 바랍니다.");
            }

                props.put("spring.datasource.password", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.password")));
                props.put("spring.datasource.username", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.username")));
                props.put("spring.datasource.url", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.url")));


        } catch (Exception e) {
            e.printStackTrace();
        }

        environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    }


    public Boolean QuestionDataSourceEncryptedYN(){


        while(true){
            System.out.print("DataSource 의 암호화는 권장되는 사항입니다. 프로퍼티 DataSource 정보에 암호화가 되어있습니까? (y/n) : ");
            String ans = sc.nextLine().toLowerCase();

            if(ans.equals("n") )
                return false;
            if(ans.equals("y")){
                System.out.println("ㅎㅇ");
                return true;
            }
            else {
                System.out.println("정확한 값을 입력해주세요.");
            }
        }

    }


}
