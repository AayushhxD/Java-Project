package com.dietplanner.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import com.dietplanner.firebase_connection.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController extends Application {
    private Stage primaryStage;
    private FirebaseService firebaseService;
    private Scene scene;

    public void setPrimaryStageScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    public void initializeLoginScene() {
        Scene loginScene = createLoginScene();
        this.scene = loginScene;
        primaryStage.setScene(loginScene);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            FileInputStream serviceAccount = new FileInputStream("Diet Planner/Diet Planner/dietplanner/src/main/resources/fx-diet.json");
            FirebaseOptions options =  FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setProjectId("fx-diet")
            .setDatabaseUrl("https://fx-diet-default-rtdb.asia-southeast1.firebasedatabase.app")
            .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = createLoginScene();
        this.scene = scene;
        primaryStage.setTitle("Diet Planner");
        primaryStage.getIcons().add(new Image("https://dw0i2gv3d32l1.cloudfront.net/uploads/stage/stage_image/100627/optimized_product_thumb_stage.jpg"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Scene createLoginScene() {
        Label titleLabel = new Label("Welcome to Diet Planner!");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;-fx-text-fill:black");

        // Create text fields and buttons
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxSize(350, 20);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxSize(350, 20);

        Button signUpButton = new Button("Sign Up");
        Button loginButton = new Button("Log In");

        signUpButton.setPrefWidth(100);
        loginButton.setPrefWidth(100);

        // Initialize Firebase service
        firebaseService = new FirebaseService(this, emailField, passwordField);

        // Set actions for buttons
        signUpButton.setOnAction(event -> firebaseService.signup());
        loginButton.setOnAction(event -> firebaseService.login());

        // Create layout containers
        VBox fieldBox = new VBox(20, emailField, passwordField);
        fieldBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(20, loginButton, signUpButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(20, titleLabel, fieldBox, buttonBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(50));

        // Set background image
        Image backgroundImage = new Image("https://img.freepik.com/premium-photo/fresh-fruits-vegetables-grey-background-healthy-eating-concept-flat-lay-copy-space_781871-346.jpg"); // Replace with your image path
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1280, 720, false, false, false, false)); // Adjust width and height here

        mainBox.setBackground(new Background(background));

        // StackPane to hold the mainBox
        StackPane root = new StackPane(mainBox);
        root.setPrefSize(1280, 720);

        return new Scene(root);
    }
    //Navigate to the Dashboard UI
    public void navigateToDashboard() {
        DashboardController dashboardController = new DashboardController();
        dashboardController.setPrimaryStage(primaryStage);
        dashboardController.initializeDashboardScene();
    }
}
