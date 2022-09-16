package ru.sinforge.mywebapplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import java.io.*;
import java.util.Objects;

@SpringBootApplication
public class MyWebApplication {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize(DataSize.of(5, DataUnit.MEGABYTES));
        multipartConfigFactory.setMaxRequestSize(DataSize.of(5, DataUnit.MEGABYTES));
        return multipartConfigFactory.createMultipartConfig();
    }

    public static void main(String[] args) throws IOException {
        CreateFirebase();
        SpringApplication.run(MyWebApplication.class, args);
    }

    private static void CreateFirebase() throws IOException {
        ClassLoader classLoader = MyWebApplication.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccount.json")).getFile());
        InputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
    }

}
