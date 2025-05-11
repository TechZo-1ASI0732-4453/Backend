package com.techzo.cambiazo.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.key.path}")
    private String keyPath;

    @Value("${firebase.bucket.name}")
    private String bucket;

    @Bean
    public Storage firebaseStorage() throws Exception {
        FileInputStream serviceAccount = new FileInputStream(keyPath);
        var credentials = ServiceAccountCredentials.fromStream(serviceAccount);
        var options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .setStorageBucket(bucket)
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
