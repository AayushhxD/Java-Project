package com.dietplanner.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MealPlanDisplayController {

    private Stage primaryStage;
    private List<MealPlanEntry> mealPlanEntries;
    private Scene previousScene;
    private TableView<MealPlanEntry> tableView;

    public MealPlanDisplayController(Stage primaryStage, List<MealPlanEntry> mealPlanEntries, Scene previousScene) {
        this.primaryStage = primaryStage;
        this.mealPlanEntries = mealPlanEntries;
        this.previousScene = previousScene;
    }

    public void initializeMealPlanDisplayScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        ComboBox<Month> monthComboBox = new ComboBox<>();
        monthComboBox.setItems(FXCollections.observableArrayList(Month.values()));
        monthComboBox.setValue(LocalDate.now().getMonth());

        ComboBox<Integer> dayComboBox = new ComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.getItems().add(i);
        }
        dayComboBox.setValue(LocalDate.now().getDayOfMonth());

        HBox dateSelector = new HBox(10, monthComboBox, dayComboBox);
        dateSelector.setAlignment(Pos.CENTER);

        tableView = new TableView<>();

        TableColumn<MealPlanEntry, String> dayColumn = new TableColumn<>("Day");
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

        TableColumn<MealPlanEntry, String> breakfastColumn = new TableColumn<>("Breakfast");
        breakfastColumn.setCellValueFactory(new PropertyValueFactory<>("breakfast"));

        TableColumn<MealPlanEntry, String> lunchColumn = new TableColumn<>("Lunch");
        lunchColumn.setCellValueFactory(new PropertyValueFactory<>("lunch"));

        TableColumn<MealPlanEntry, String> dinnerColumn = new TableColumn<>("Dinner");
        dinnerColumn.setCellValueFactory(new PropertyValueFactory<>("dinner"));

        double columnWidth = 300.0;
        dayColumn.setMinWidth(columnWidth);
        breakfastColumn.setMinWidth(columnWidth);
        lunchColumn.setMinWidth(columnWidth);
        dinnerColumn.setMinWidth(columnWidth);

        centerAlignColumn(dayColumn);
        centerAlignColumn(breakfastColumn);
        centerAlignColumn(lunchColumn);
        centerAlignColumn(dinnerColumn);

        tableView.getColumns().addAll(dayColumn, breakfastColumn, lunchColumn, dinnerColumn);

        ObservableList<MealPlanEntry> data = FXCollections.observableArrayList(mealPlanEntries);
        tableView.setItems(data);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setStyle("-fx-background-color: white; -fx-table-cell-border-color: transparent;");
        tableView.setStyle("-fx-font-size: 18px;");

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        backButton.setOnAction(event -> primaryStage.setScene(previousScene));

        Button saveButton = new Button("Save Date");
        saveButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        saveButton.setOnAction(event -> saveDate(monthComboBox.getValue(), dayComboBox.getValue()));

        HBox buttonBox = new HBox(10, backButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(dateSelector, tableView, buttonBox);

        StackPane stackPane = new StackPane();

        Image backgroundImage = new Image("https://img.freepik.com/premium-photo/fresh-fruits-vegetables-grey-background-healthy-eating-concept-flat-lay-copy-space_781871-346.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1280);
        backgroundImageView.setFitHeight(720);
        backgroundImageView.setPreserveRatio(false);

        stackPane.getChildren().addAll(backgroundImageView, root);
        StackPane.setAlignment(backgroundImageView, Pos.CENTER);
        StackPane.setAlignment(root, Pos.CENTER);

        Scene scene = new Scene(stackPane, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private <T> void centerAlignColumn(TableColumn<MealPlanEntry, T> column) {
        column.setCellFactory(new Callback<TableColumn<MealPlanEntry, T>, TableCell<MealPlanEntry, T>>() {
            @Override
            public TableCell<MealPlanEntry, T> call(TableColumn<MealPlanEntry, T> param) {
                return new TableCell<MealPlanEntry, T>() {
                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.toString());
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        });
    }

    private void saveDate(Month month, int day) {
        // Replace this with your desired save action
        System.out.println("Saving date: " + month + " " + day);
    }
}
