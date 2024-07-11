package com.dietplanner.Controller;

import com.dietplanner.firebase_connection.FirestoreInitializer;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProgressTrackingController {
    private Stage primaryStage;
    private Scene progressTrackingScene;
    private Firestore db;

    public ProgressTrackingController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.db = FirestoreInitializer.initializeFirestore();
        if (db == null) {
            System.out.println("Firestore initialization failed.");
        }
    }

    public void initializeProgressTrackingScene() {
        VBox progressTrackingBox = new VBox(20);
        progressTrackingBox.setAlignment(Pos.CENTER);
        progressTrackingBox.setPadding(new Insets(50));

        Text sceneTitle = new Text("Progress Tracking");
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        TableView<ProgressEntry> tableView = createProgressTrackingTable();
        populateProgressData(tableView);

        HBox inputBox = createInputBox(tableView);
        HBox actionButtonsBox = createActionButtonsBox(tableView);

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> primaryStage.setScene(DashboardController.getDashboardScene()));

        progressTrackingBox.getChildren().addAll(sceneTitle, tableView, inputBox, actionButtonsBox, goBackButton);

        // Set background image for progress tracking scene
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("https://img.freepik.com/premium-photo/fresh-fruits-vegetables-grey-background-healthy-eating-concept-flat-lay-copy-space_781871-346.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        progressTrackingBox.setBackground(new Background(backgroundImage));

        progressTrackingScene = new Scene(progressTrackingBox, 1280, 720);
        primaryStage.setScene(progressTrackingScene);
    }

    private TableView<ProgressEntry> createProgressTrackingTable() {
        TableView<ProgressEntry> tableView = new TableView<>();

        TableColumn<ProgressEntry, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setMinWidth(150);

        TableColumn<ProgressEntry, Double> weightColumn = new TableColumn<>("Weight (kg)");
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        weightColumn.setMinWidth(100);

        TableColumn<ProgressEntry, Integer> caloriesColumn = new TableColumn<>("Calories Consumed");
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("caloriesConsumed"));
        caloriesColumn.setMinWidth(150);

        TableColumn<ProgressEntry, Integer> exerciseDurationColumn = new TableColumn<>("Exercise Duration (min)");
        exerciseDurationColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseDuration"));
        exerciseDurationColumn.setMinWidth(150);

        tableView.getColumns().addAll(dateColumn, weightColumn, caloriesColumn, exerciseDurationColumn);

        return tableView;
    }

    private HBox createInputBox(TableView<ProgressEntry> tableView) {
        HBox inputBox = new HBox(10);
        inputBox.setAlignment(Pos.CENTER);

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date");

        TextField weightField = new TextField();
        weightField.setPromptText("Weight (kg)");

        TextField caloriesField = new TextField();
        caloriesField.setPromptText("Calories Consumed");

        TextField exerciseDurationField = new TextField();
        exerciseDurationField.setPromptText("Exercise Duration (min)");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            try {
                LocalDate date = datePicker.getValue();
                double weight = Double.parseDouble(weightField.getText());
                int caloriesConsumed = Integer.parseInt(caloriesField.getText());
                int exerciseDuration = Integer.parseInt(exerciseDurationField.getText());

                ProgressEntry newEntry = new ProgressEntry(date, weight, caloriesConsumed, exerciseDuration);
                tableView.getItems().add(newEntry);

                datePicker.setValue(null);
                weightField.clear();
                caloriesField.clear();
                exerciseDurationField.clear();

                // Save to Firestore
                saveProgressEntryToFirestore(newEntry);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input: " + ex.getMessage());
                showAlert("Input Error", "Please enter valid values for all fields.");
            }
        });

        inputBox.getChildren().addAll(datePicker, weightField, caloriesField, exerciseDurationField, addButton);

        return inputBox;
    }

    private HBox createActionButtonsBox(TableView<ProgressEntry> tableView) {
        HBox actionButtonsBox = new HBox(10);
        actionButtonsBox.setAlignment(Pos.CENTER);

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            ProgressEntry selectedEntry = tableView.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                TextInputDialog weightDialog = new TextInputDialog(Double.toString(selectedEntry.getWeight()));
                weightDialog.setTitle("Edit Weight");
                weightDialog.setHeaderText("Edit Weight");
                weightDialog.setContentText("Weight (kg):");

                TextInputDialog caloriesDialog = new TextInputDialog(Integer.toString(selectedEntry.getCaloriesConsumed()));
                caloriesDialog.setTitle("Edit Calories Consumed");
                caloriesDialog.setHeaderText("Edit Calories Consumed");
                caloriesDialog.setContentText("Calories Consumed:");

                TextInputDialog exerciseDialog = new TextInputDialog(Integer.toString(selectedEntry.getExerciseDuration()));
                exerciseDialog.setTitle("Edit Exercise Duration");
                exerciseDialog.setHeaderText("Edit Exercise Duration");
                exerciseDialog.setContentText("Exercise Duration (min):");

                weightDialog.showAndWait().ifPresent(weight -> selectedEntry.setWeight(Double.parseDouble(weight)));
                caloriesDialog.showAndWait().ifPresent(calories -> selectedEntry.setCaloriesConsumed(Integer.parseInt(calories)));
                exerciseDialog.showAndWait().ifPresent(duration -> selectedEntry.setExerciseDuration(Integer.parseInt(duration)));
                tableView.refresh();

                // Update in Firestore
                updateProgressEntryInFirestore(selectedEntry);
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            ProgressEntry selectedEntry = tableView.getSelectionModel().getSelectedItem();
            if (selectedEntry != null) {
                tableView.getItems().remove(selectedEntry);
                // Delete from Firestore
                deleteProgressEntryFromFirestore(selectedEntry);
            }
        });

        actionButtonsBox.getChildren().addAll(editButton, deleteButton);

        return actionButtonsBox;
    }

    private void populateProgressData(TableView<ProgressEntry> tableView) {
        ObservableList<ProgressEntry> data = FXCollections.observableArrayList(
                new ProgressEntry(LocalDate.of(2023, 7, 1), 70.5, 1800, 30),
                new ProgressEntry(LocalDate.of(2023, 7, 2), 70.0, 1750, 45),
                new ProgressEntry(LocalDate.of(2023, 7, 3), 69.8, 1600, 60)
                // Add more entries as needed
        );

        tableView.setItems(data);
    }

    public Scene getProgressTrackingScene() {
        return progressTrackingScene;
    }

    private void saveProgressEntryToFirestore(ProgressEntry entry) {
        if (db == null) {
            System.out.println("Firestore db is null.");
            return;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("date", entry.getDate().toString());
        data.put("weight", entry.getWeight());
        data.put("caloriesConsumed", entry.getCaloriesConsumed());
        data.put("exerciseDuration", entry.getExerciseDuration());

        ApiFuture<DocumentReference> result = db.collection("progressEntries").add(data);
        result.addListener(() -> {
            try {
                System.out.println("Progress entry saved with ID: " + result.get().getId());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                System.out.println("Failed to save progress entry: " + e.getMessage());
            }
        }, Runnable::run);
    }

    private void updateProgressEntryInFirestore(ProgressEntry entry) {
        if (db == null) {
            System.out.println("Firestore db is null.");
            return;
        }
        try {
            ApiFuture<QuerySnapshot> future = db.collection("progressEntries")
                    .whereEqualTo("date", entry.getDate().toString())
                    .get();
            future.addListener(() -> {
                try {
                    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                    if (!documents.isEmpty()) {
                        DocumentReference docRef = documents.get(0).getReference();
                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("weight", entry.getWeight());
                        updatedData.put("caloriesConsumed", entry.getCaloriesConsumed());
                        updatedData.put("exerciseDuration", entry.getExerciseDuration());
                        docRef.update(updatedData);
                        System.out.println("Progress entry updated");
                    } else {
                        System.out.println("No matching documents found for update.");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }, Runnable::run);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteProgressEntryFromFirestore(ProgressEntry entry) {
        if (db == null) {
            System.out.println("Firestore db is null.");
            return;
        }
        try {
            ApiFuture<QuerySnapshot> future = db.collection("progressEntries")
                    .whereEqualTo("date", entry.getDate().toString())
                    .get();
            future.addListener(() -> {
                try {
                    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                    if (!documents.isEmpty()) {
                        DocumentReference docRef = documents.get(0).getReference();
                        docRef.delete();
                        System.out.println("Progress entry deleted");
                    } else {
                        System.out.println("No matching documents found for delete.");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }, Runnable::run);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
