package com.techzo.cambiazo.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.key.path}")
    private String keyPath;

    @Value("${firebase.bucket.name}")
    private String bucket;

    @Bean
    public Storage firebaseStorage() throws Exception {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("config/firebase-key.json");

        if (serviceAccount == null) {
            throw new FileNotFoundException("No se encontró el archivo firebase-key.json en resources/config");
        }

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
