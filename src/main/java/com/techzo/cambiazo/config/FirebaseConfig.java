package com.techzo.cambiazo.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.bucket.name}")
    private String bucket;

    @Bean
    public Storage firebaseStorage() throws Exception {
        String base64Json = System.getenv("FIREBASE_KEY_JSON");
        if (base64Json == null || base64Json.isBlank()) {
            throw new IllegalStateException("FIREBASE_KEY_JSON environment variable is not set.");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(base64Json);
        InputStream serviceAccount = new ByteArrayInputStream(decodedBytes);

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
