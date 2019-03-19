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
                System.out.println("");
                System.out.print("Enter Datasource username to encrypt : ");
                String userName = sc.nextLine();
                System.out.print("Enter Datasource password to encrypt : ");
                String password = sc.nextLine();
                System.out.print("Enter Datasource url to encrypt [ex) jdbc:<dbname>:<connection method>:@<ip>:<port>:<sid>] : ");
                String url = sc.nextLine();

                System.out.println("");
                System.out.println("------- Encrypted Information -------");
                System.out.println("encrypted spring.datasource.username : " + AES256Cipher.getInstance().AES_Encode(userName));
                System.out.println("encrypted spring.datasource.password : " + AES256Cipher.getInstance().AES_Encode(password));
                System.out.println("encrypted spring.datasource.url : " + AES256Cipher.getInstance().AES_Encode(url));
                System.out.println("------- Encrypted Information -------");
                System.out.println("");

                System.out.println("Encrypted with phrases on the then modify the data source and restart the server again, please.");
                System.out.println("");
            }

                props.put("spring.datasource.username", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.username")));
                props.put("spring.datasource.password", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.password")));
                props.put("spring.datasource.url", AES256Cipher.getInstance().AES_Decode(environment.getProperty("spring.datasource.url")));


        } catch (Exception e) {
            e.printStackTrace();
        }

        environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    }


    public Boolean QuestionDataSourceEncryptedYN(){


        while(true){
            System.out.print("Encrypting the data source is a recommended practice. Is the Propriety DataSource encrypted? (y/n) : ");
            String ans = sc.nextLine().toLowerCase();

            if(ans.equals("n") )
                return false;
            if(ans.equals("y")){
                return true;
            }
            else {
                System.out.println("Please enter the correct value.");
            }
        }

    }


}
