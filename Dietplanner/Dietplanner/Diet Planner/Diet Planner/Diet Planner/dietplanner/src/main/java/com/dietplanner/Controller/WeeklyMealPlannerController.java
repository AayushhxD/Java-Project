package com.dietplanner.Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class WeeklyMealPlannerController {

    private Stage primaryStage;
    private List<MealPlanEntry> mealPlanEntries;
    private Scene weeklyMealPlannerScene; // Store the scene to pass it later

    public WeeklyMealPlannerController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.mealPlanEntries = new ArrayList<>();
    }

    public void initializeWeeklyMealPlannerScene() {
        VBox progressTrackingBox = createProgressTrackingBox();

        StackPane root = new StackPane();
        root.getChildren().add(setBackgroundImage());
        root.getChildren().add(progressTrackingBox);

        weeklyMealPlannerScene = new Scene(root, 1280, 720); // Initialize the scene
        primaryStage.setScene(weeklyMealPlannerScene);
    }

    private VBox createProgressTrackingBox() {
        VBox progressTrackingBox = new VBox(20);
        progressTrackingBox.setAlignment(Pos.CENTER);
        progressTrackingBox.setPadding(new Insets(50));

        Text title = new Text("Weekly Meal Planner");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        GridPane mealPlanGrid = new GridPane();
        mealPlanGrid.setAlignment(Pos.CENTER);
        mealPlanGrid.setHgap(10);
        mealPlanGrid.setVgap(10);

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        TextField[] breakfastFields = new TextField[7];
        TextField[] lunchFields = new TextField[7];
        TextField[] dinnerFields = new TextField[7];

        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            mealPlanGrid.add(dayLabel, 0, i);

            breakfastFields[i] = new TextField();
            breakfastFields[i].setPromptText("Breakfast");

            lunchFields[i] = new TextField();
            lunchFields[i].setPromptText("Lunch");

            dinnerFields[i] = new TextField();
            dinnerFields[i].setPromptText("Dinner");

            HBox mealBox = new HBox(10, breakfastFields[i], lunchFields[i], dinnerFields[i]);
            mealPlanGrid.add(mealBox, 1, i);
        }

        Button generatePlanButton = new Button("Generate Weekly Meal Plan");
        generatePlanButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        generatePlanButton.setOnAction(event -> {
            generateWeeklyMealPlan(days, breakfastFields, lunchFields, dinnerFields);
            displayStoredMealPlans();
        });

        Button viewMealPlanButton = new Button("View Meal Plan");
        viewMealPlanButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        viewMealPlanButton.setOnAction(event -> displayStoredMealPlans());

        Button backButton = new Button("Back to Dashboard");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        backButton.setOnAction(event -> goBackToDashboard());

        progressTrackingBox.getChildren().addAll(title, mealPlanGrid, generatePlanButton, viewMealPlanButton, backButton);

        return progressTrackingBox;
    }

    private void generateWeeklyMealPlan(String[] days, TextField[] breakfastFields, TextField[] lunchFields, TextField[] dinnerFields) {
        mealPlanEntries.clear();  // Clear existing entries before adding new ones
        for (int i = 0; i < days.length; i++) {
            String breakfast = breakfastFields[i].getText();
            String lunch = lunchFields[i].getText();
            String dinner = dinnerFields[i].getText();
            mealPlanEntries.add(new MealPlanEntry(days[i], breakfast, lunch, dinner));
        }
        System.out.println("Weekly meal plan generated!");
    }

    private void displayStoredMealPlans() {
        MealPlanDisplayController displayController = new MealPlanDisplayController(primaryStage, mealPlanEntries, weeklyMealPlannerScene);
        displayController.initializeMealPlanDisplayScene();
    }

    private void goBackToDashboard() {
        DashboardController dashboardController = new DashboardController();
        dashboardController.setPrimaryStage(primaryStage);
    }

    private Pane setBackgroundImage() {
        Image backgroundImage = new Image("https://img.freepik.com/premium-photo/fresh-fruits-vegetables-grey-background-healthy-eating-concept-flat-lay-copy-space_781871-346.jpg");

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1280);
        backgroundImageView.setFitHeight(720);
        backgroundImageView.setPreserveRatio(false);

        Pane backgroundPane = new Pane();
        backgroundPane.getChildren().add(backgroundImageView);

        return backgroundPane;
    }
}
