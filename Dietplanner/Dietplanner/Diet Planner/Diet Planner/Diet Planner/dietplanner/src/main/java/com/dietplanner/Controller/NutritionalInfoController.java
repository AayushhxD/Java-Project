package com.dietplanner.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NutritionalInfoController {
    private Stage primaryStage;
    private Scene nutritionalInfoScene;

    public NutritionalInfoController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initializeNutritionalInfoScene() {
        VBox nutritionalInfoBox = new VBox(20);
        nutritionalInfoBox.setAlignment(Pos.CENTER);
        nutritionalInfoBox.setPadding(new Insets(50));

        Text sceneTitle = new Text("Nutritional Info");
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        TableView<FoodItem> tableView = createNutritionalTable();
        populateNutritionalData(tableView);

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> primaryStage.setScene(DashboardController.getDashboardScene()));

        nutritionalInfoBox.getChildren().addAll(sceneTitle, tableView, goBackButton);

        // Set background image for nutritional info scene
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("https://img.freepik.com/premium-photo/fresh-fruits-vegetables-grey-background-healthy-eating-concept-flat-lay-copy-space_781871-346.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        nutritionalInfoBox.setBackground(new Background(backgroundImage));

        nutritionalInfoScene = new Scene(nutritionalInfoBox, 1280, 720);
        primaryStage.setScene(nutritionalInfoScene);
    }

    private TableView<FoodItem> createNutritionalTable() {
        TableView<FoodItem> tableView = new TableView<>();

        TableColumn<FoodItem, String> foodNameColumn = new TableColumn<>("Food Name");
        foodNameColumn.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        foodNameColumn.setMinWidth(200);

        TableColumn<FoodItem, Integer> caloriesColumn = new TableColumn<>("Calories (kcal)");
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        caloriesColumn.setMinWidth(100);

        TableColumn<FoodItem, Integer> totalFatColumn = new TableColumn<>("Total Fat (g)");
        totalFatColumn.setCellValueFactory(new PropertyValueFactory<>("totalFat"));
        totalFatColumn.setMinWidth(100);

        TableColumn<FoodItem, Integer> proteinColumn = new TableColumn<>("Protein (g)");
        proteinColumn.setCellValueFactory(new PropertyValueFactory<>("protein"));
        proteinColumn.setMinWidth(100);

        tableView.getColumns().addAll(foodNameColumn, caloriesColumn, totalFatColumn, proteinColumn);

        return tableView;
    }

    private void populateNutritionalData(TableView<FoodItem> tableView) {
        ObservableList<FoodItem> data = FXCollections.observableArrayList(
                new FoodItem("Apple", 52, 0, 1),
                new FoodItem("Banana", 89, 0, 1),
                new FoodItem("Orange", 47, 0, 1),
                new FoodItem("Broccoli", 34, 0, 2),
                new FoodItem("Chicken Breast", 165, 3, 30),
                new FoodItem("Salmon", 208, 13, 25),
                new FoodItem("Milk", 42, 1, 4),
                new FoodItem("Peanut Butter", 188, 16, 8),
                new FoodItem("Oats", 389, 6, 17),
                new FoodItem("Avocado", 160, 15, 2),
                new FoodItem("Yogurt", 59, 1, 6),
                new FoodItem("Brown Bread", 79, 1, 3),
                new FoodItem("Egg", 68, 5, 6),
                new FoodItem("Sweet Potato", 90, 0, 2),
                new FoodItem("Paneer", 265, 22, 18),
                new FoodItem("Soya Chunks", 345, 1, 52)
        );

        tableView.setItems(data);
    }

    public Scene getNutritionalInfoScene() {
        return nutritionalInfoScene;
    }

    // FoodItem class
    public static class FoodItem {
        private String foodName;
        private int calories;
        private int totalFat;
        private int protein;

        public FoodItem(String foodName, int calories, int totalFat, int protein) {
            this.foodName = foodName;
            this.calories = calories;
            this.totalFat = totalFat;
            this.protein = protein;
        }

        // Getters and setters
        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public int getTotalFat() {
            return totalFat;
        }

        public void setTotalFat(int totalFat) {
            this.totalFat = totalFat;
        }

        public int getProtein() {
            return protein;
        }

        public void setProtein(int protein) {
            this.protein = protein;
        }
    }
}
