package com.dietplanner.firebase_connection;

import com.dietplanner.Controller.LoginController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FirebaseService {
    private TextField emailField;
    private PasswordField passwordField;
    private LoginController loginController;

    public FirebaseService(LoginController loginController, TextField emailField, PasswordField passwordField) {
        this.loginController = loginController;
        this.emailField = emailField;
        this.passwordField = passwordField;
    }

    public boolean signup() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPassword(password)
                    .setDisabled(false);

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            System.out.println("Successfully created user: " + userRecord.getUid());
            showAlert("Success", "User created successfully");
            return true;

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            System.out.println("Error code: " + e.getAuthErrorCode().name());
            System.out.println("Error message: " + e.getMessage());
            showAlert("Error", "Failed to create user: " + e.getMessage());
            return false;
        }
    }

    public boolean login() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            String apiKey = "AIzaSyAKscX-sZdRX75qt6WgDCOI6lff_7PRoUo";
            URL url = new URL("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("email", email);
            jsonRequest.put("password", password);
            jsonRequest.put("returnSecureToken", true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() == 200) {
                showAlert("Success", "Login successful!");
                loginController.navigateToDashboard(); 
                return true;
            } else {
                showAlert("Invalid Login", "Invalid credentials!!!");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred: " + e.getMessage());
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
