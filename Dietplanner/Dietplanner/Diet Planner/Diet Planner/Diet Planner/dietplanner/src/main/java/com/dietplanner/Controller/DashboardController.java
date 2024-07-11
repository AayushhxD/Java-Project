package com.dietplanner.Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardController {
    private Stage primaryStage;
    private static Scene dashboardScene;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeDashboardScene();
    }

    public static Scene getDashboardScene() {
        return dashboardScene;
    }

    public void initializeDashboardScene() {
        VBox dashboardBox = createDashboardBox();

        dashboardScene = new Scene(dashboardBox, 1280, 720); // Adjust size as needed

        // Set the dashboard scene
        primaryStage.setScene(dashboardScene);
    }

    private VBox createDashboardBox() {
        VBox dashboardBox = new VBox(20);
        dashboardBox.setAlignment(Pos.CENTER);
        dashboardBox.setPadding(new Insets(50));

        // Create title text
        Text title = new Text("Diet Planner");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Create an HBox to hold buttons horizontally
        HBox buttonContainer = new HBox(20); // spacing between buttons
        buttonContainer.setAlignment(Pos.CENTER);

        // Create buttons with background images
        Button box1 = createButtonWithBackground("Meal Planner", "https://img.freepik.com/free-photo/top-view-arrangement-with-diet-planning-notepad_23-2149099886.jpg?size=626&ext=jpg&ga=GA1.1.672697106.1717632000&semt=ais_user");
        Button box2 = createButtonWithBackground("Progress Tracking", "https://img.freepik.com/free-photo/oatmeal-bowl-with-fruits-blue-background_23-2148341652.jpg?t=st=1720465708~exp=1720469308~hmac=55170860f1bc2df2e395b9aa024e0850cab246d53c2922e1a951b66865e7612f&w=740");
        Button box3 = createButtonWithBackground("Nutritional Info", "https://as2.ftcdn.net/v2/jpg/02/41/31/81/1000_F_241318182_DeWOTrJBMao9H1v01VYC4x0vplkO9KdD.jpg");

        // Add buttons to the HBox
        buttonContainer.getChildren().addAll(box1, box2, box3);

        // Set background image for dashboard to cover entire screen
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("https://img.freepik.com/premium-photo/fresh-fruits-vegetables-grey-background-healthy-eating-concept-flat-lay-copy-space_781871-346.jpg"), // Replace with your image URL
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)); // Adjust width and height to cover entire screen

        dashboardBox.setBackground(new Background(backgroundImage));

        // Add the title and the HBox (buttonContainer) to the VBox (dashboardBox)
        dashboardBox.getChildren().addAll(title, buttonContainer);

        return dashboardBox;
    }

    private Button createButtonWithBackground(String text, String imageUrl) {
        Button button = new Button(text);
        button.setPrefSize(200, 200); // Set size of the box
        button.setStyle("-fx-background-image: url('" + imageUrl + "'); " +
                        "-fx-background-size: cover; " +
                        "-fx-text-fill: black; " +
                        "-fx-font-weight: bold;");

        // Redirect to new scenes based on button text
        button.setOnAction(event -> {
            switch (text) {
                case "Meal Planner":
                    showMealPlannerScene();
                    break;
                case "Progress Tracking":
                    showProgressTrackingScene();
                    break;
                case "Nutritional Info":
                    showNutritionalInfoScene();
                    break;
                default:
                    System.out.println("Clicked " + text);
            }
        });

        return button;
    }

    // Method to show Meal Planner Scene
    private void showMealPlannerScene() {
        MealPlannerController mealPlannerController = new MealPlannerController(primaryStage);
        mealPlannerController.initializeMealPlannerScene();
    }

    // Method to show Progress Tracking Scene
    private void showProgressTrackingScene() {
        ProgressTrackingController progressTrackingController = new ProgressTrackingController(primaryStage);
        progressTrackingController.initializeProgressTrackingScene();

        // Scene progressTrackingScene = new Scene(progressTrackingBox, 1280, 720);
        // primaryStage.setScene(progressTrackingScene);
    }

    // Method to show Nutritional Info Scene
    private void showNutritionalInfoScene() {
        NutritionalInfoController nutritionalInfoController = new NutritionalInfoController(primaryStage);
        nutritionalInfoController.initializeNutritionalInfoScene();
    }
}
