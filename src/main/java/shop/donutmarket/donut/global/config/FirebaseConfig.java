package shop.donutmarket.donut.global.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
public class FirebaseConfig {

    private final AmazonS3 amazonS3;

    @PostConstruct
    public void init(){
        try{
            // json 파일의 인증 정보 가져오기

            S3Object s3Object = amazonS3.getObject("groupbuying2", "firebase-adminsdk.json");
            InputStream inputStream = s3Object.getObjectContent();

            // GoogleCredentials 생성
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();
                    
            // firebase 초기화
            FirebaseApp.initializeApp(options);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}