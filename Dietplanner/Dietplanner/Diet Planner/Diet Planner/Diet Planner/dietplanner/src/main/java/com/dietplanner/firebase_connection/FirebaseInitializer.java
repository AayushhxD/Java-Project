// package com.dietplanner.firebase_connection;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.cloud.firestore.Firestore;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import com.google.firebase.cloud.FirestoreClient;

// import java.io.FileInputStream;
// import java.io.IOException;

// public class FirebaseInitializer {

//     private static Firestore firestore;

//     public static void initialize() throws IOException {
//         FileInputStream serviceAccount = new FileInputStream("Diet Planner/Diet Planner/dietplanner/src/main/resources/fx-diet.json");

//         FirebaseOptions options =  FirebaseOptions.builder()
//                 .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                 .setProjectId("fx-diet")
//                 .build();

//         FirebaseApp.initializeApp(options);
//         firestore = FirestoreClient.getFirestore();
//     }

//     public static Firestore getFirestore() {
//         return firestore;
//     }
// }
