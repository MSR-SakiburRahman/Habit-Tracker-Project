package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habit;
import model.HabitManager;

public class HabitListView {

    private HabitManager habitManager;

    public HabitListView(HabitManager habitManager) {
        this.habitManager = habitManager;
    }

    public void showListView() {
        // Create a new stage for the list view
        Stage listStage = new Stage();
        listStage.setTitle("Habit List");

        // Layout for the list view
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-spacing: 10;");

        // Observable list for habits
        ObservableList<Habit> habitObservableList = FXCollections.observableArrayList(habitManager.getHabits());

        // ListView for displaying habits
        ListView<Habit> habitListView = new ListView<>(habitObservableList);
        habitListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Habit habit, boolean empty) {
                super.updateItem(habit, empty);
                if (empty || habit == null || habit.getName() == null) {
                    setText(null);
                } else {
                    setText(habit.getName() + (habit.isMeasurable() ? " (Target: " + habit.getTargetValue() + ")" : ""));
                }
            }
        });

        // Buttons for actions
        Button viewButton = new Button("View");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        // Event handling
        viewButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                new HabitDetailView(selectedHabit).showDetails();
            } else {
                showError("No habit selected.");
            }
        });

        editButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                new HabitFormView(habitManager, selectedHabit).showForm();
                habitObservableList.setAll(habitManager.getHabits()); // Refresh the list
            } else {
                showError("No habit selected.");
            }
        });

        deleteButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                habitManager.removeHabit(selectedHabit);
                habitObservableList.setAll(habitManager.getHabits()); // Refresh the list
            } else {
                showError("No habit selected.");
            }
        });

        // Layout for buttons
        HBox buttonLayout = new HBox(10, viewButton, editButton, deleteButton);
        buttonLayout.setStyle("-fx-alignment: center;");

        // Add components to layout
        layout.getChildren().addAll(habitListView, buttonLayout);

        // Scene setup
        Scene scene = new Scene(layout, 400, 400);
        listStage.setScene(scene);
        listStage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
