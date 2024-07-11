package com.dietplanner.firebase_connection;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.FileInputStream;
import java.io.IOException;

public class FirestoreInitializer {

    public static Firestore initializeFirestore() {
        Firestore db = null;
        try {
            FileInputStream serviceAccount = new FileInputStream("Diet Planner/Diet Planner/dietplanner/src/main/resources/fx-diet.json");
            FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("fx-diet")
                    .build();
            db = firestoreOptions.getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return db;
    }
    
}
