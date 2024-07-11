package com.dietplanner.Controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MealPlannerController {

    private Stage primaryStage;
    private Scene mealPlannerScene;
    private VBox mealSuggestionsBox;

    public MealPlannerController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeMealPlannerScene();
    }

    public void initializeMealPlannerScene() {
        VBox mealPlannerBox = new VBox(20);
        mealPlannerBox.setAlignment(Pos.CENTER);
        mealPlannerBox.setPadding(new Insets(50));

        // Set background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("https://img.freepik.com/premium-photo/fresh-fruits-vegetables-grey-background-healthy-eating-concept-flat-lay-copy-space_781871-346.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        mealPlannerBox.setBackground(new Background(backgroundImage));

        // Title
        Text title = new Text("Meal Planner");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setStyle("-fx-fill: #000000;");

        // Left section with an image
        ImageView imageView = new ImageView(new Image("https://images.squarespace-cdn.com/content/v1/556fa783e4b0b65c3516abe8/1604882139936-XYCYSQHJ3V06JF5YW6EI/healthy+food+vertical.png"));
        imageView.setFitWidth(320);
        imageView.setFitHeight(700); 
        VBox imageBox = new VBox(imageView);
        imageBox.setAlignment(Pos.CENTER_LEFT);
        imageBox.setPadding(new Insets(0, 50, 60, 0)); // Padding for image section

        // Right section with user input and controls
        GridPane inputBox = new GridPane();
        inputBox.setVgap(10);
        inputBox.setHgap(10);
        inputBox.setAlignment(Pos.CENTER_LEFT);

        Label goalLabel = new Label("Select your goal:");
        ComboBox<String> goalComboBox = new ComboBox<>();
        goalComboBox.getItems().addAll("Lose Weight", "Gain Weight", "Maintain Weight");

        Label weightLabel = new Label("Enter your current weight (kg):");
        TextField weightField = new TextField();

        Label heightLabel = new Label("Enter your height (cm):");
        TextField heightField = new TextField();

        Label ageLabel = new Label("Enter your age:");
        TextField ageField = new TextField();

        Label genderLabel = new Label("Select your gender:");
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female");

        Button generatePlanButton = new Button("Generate Diet Plan");
        generatePlanButton.setStyle("-fx-font-size: 16px; -fx-background-color: #00bfff; -fx-text-fill: #ffffff;");
        generatePlanButton.setPrefWidth(200);
        generatePlanButton.setOnAction(event -> generateDietPlan(goalComboBox.getValue(), weightField.getText(), heightField.getText(), ageField.getText(), genderComboBox.getValue()));

        // Weekly Meal Planning Button
        Button weeklyMealPlanningButton = new Button("Weekly Meal Planning");
        weeklyMealPlanningButton.setStyle("-fx-font-size: 16px; -fx-background-color: #32CD32; -fx-text-fill: #ffffff;");
        weeklyMealPlanningButton.setPrefWidth(200);
        weeklyMealPlanningButton.setOnAction(event -> {
            WeeklyMealPlannerController weeklyMealPlannerController = new WeeklyMealPlannerController(primaryStage);
            weeklyMealPlannerController.initializeWeeklyMealPlannerScene(); // Initialize the weekly meal planner scene
        });

        // Back Button
        Button goBackButton = new Button("Go Back");
        goBackButton.setStyle("-fx-font-size: 16px; -fx-background-color: #ff0000; -fx-text-fill: #ffffff;");
        goBackButton.setPrefWidth(200);
        goBackButton.setOnAction(e -> primaryStage.setScene(DashboardController.getDashboardScene()));

        // Section to display the meal suggestions
        mealSuggestionsBox = new VBox(10);
        mealSuggestionsBox.setAlignment(Pos.CENTER); // Center alignment for the suggestions

        // Adding styles to labels and text fields
        goalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000;");
        weightLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000;");
        heightLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000;");
        ageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000;");
        genderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #000000;");

        weightField.setStyle("-fx-font-size: 16px;");
        heightField.setStyle("-fx-font-size: 16px;");
        ageField.setStyle("-fx-font-size: 16px;");

        // Arrange components in the grid
        inputBox.add(goalLabel, 0, 0);
        inputBox.add(goalComboBox, 1, 0);
        inputBox.add(weightLabel, 0, 1);
        inputBox.add(weightField, 1, 1);
        inputBox.add(heightLabel, 0, 2);
        inputBox.add(heightField, 1, 2);
        inputBox.add(ageLabel, 0, 3);
        inputBox.add(ageField, 1, 3);
        inputBox.add(genderLabel, 0, 4);
        inputBox.add(genderComboBox, 1, 4);
        inputBox.add(generatePlanButton, 0, 5, 1, 1); // Align Generate Plan Button
        inputBox.add(goBackButton, 1, 5, 1, 1); // Align Go Back Button
        inputBox.add(weeklyMealPlanningButton, 0, 6, 2, 1); // Span across two columns for the weekly meal planning button

        VBox rightSection = new VBox(30); // Container for inputBox and mealSuggestionsBox
        rightSection.setAlignment(Pos.CENTER); // Center alignment for the right section
        rightSection.getChildren().addAll(inputBox, mealSuggestionsBox);

        HBox contentBox = new HBox(50);
        contentBox.getChildren().addAll(imageBox, rightSection);

        // Adding elements to the layout in a meaningful order
        mealPlannerBox.getChildren().addAll(title, contentBox);

        mealPlannerScene = new Scene(mealPlannerBox, 1280, 720);
        primaryStage.setScene(mealPlannerScene);
    }

    private void generateDietPlan(String goal, String weight, String height, String age, String gender) {
        // Calculate daily caloric needs
        double dailyCalories = calculateDailyCaloricNeeds(goal, Double.parseDouble(weight), Double.parseDouble(height), Integer.parseInt(age), gender);

        // Generate meal suggestions
        String mealSuggestions = generateMealSuggestions(dailyCalories);

        // Display meal suggestions
        displayMealSuggestions(mealSuggestions);
    }

    private double calculateDailyCaloricNeeds(String goal, double weight, double height, int age, String gender) {
        // BMR calculation
        double bmr;
        if (gender.equals("Male")) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        // Adjust BMR based on the goal
        double dailyCalories = bmr;
        switch (goal) {
            case "Lose Weight":
                dailyCalories -= 500; // Subtract 500 calories for weight loss
                break;
            case "Gain Weight":
                dailyCalories += 500; // Add 500 calories for weight gain
                break;
            case "Maintain Weight":
                // No change needed
                break;
        }

        return dailyCalories;
    }

    private String generateMealSuggestions(double dailyCalories) {
        // Placeholder logic for meal suggestions based on daily caloric needs
        String mealSuggestions = "";

        // Example suggestion logic based on caloric needs
        if (dailyCalories < 1500) {
            mealSuggestions = "Your daily caloric needs are very low. Please consult a nutritionist.";
        } else if (dailyCalories < 2000) {
            mealSuggestions = "Breakfast: 400 calories\nLunch: 600 calories\nDinner: 600 calories\nSnacks: 400 calories";
        } else {
            mealSuggestions = "Breakfast: 500 calories\nLunch: 700 calories\nDinner: 700 calories\nSnacks: 500 calories";
        }

        return mealSuggestions;
    }

    private void displayMealSuggestions(String mealSuggestions) {
        // Update the mealSuggestionsBox with the meal suggestions
        mealSuggestionsBox.getChildren().clear();
        
        // Set background for mealSuggestionsBox
        BackgroundImage mealSuggestionsBackground = new BackgroundImage(
                new Image("https://htmlcolorcodes.com/assets/images/colors/steel-gray-color-solid-background-1920x1080.png"), // Replace with your actual background image URL
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, false)
        );
        mealSuggestionsBox.setBackground(new Background(mealSuggestionsBackground));

        for (String suggestion : mealSuggestions.split("\n")) {
            Text suggestionText = new Text(suggestion);
            suggestionText.setStyle("-fx-font-size: 16px; -fx-fill: #000000;");
            mealSuggestionsBox.getChildren().add(suggestionText);
        }
    }
}
