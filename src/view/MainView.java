package view;

import controller.HabitController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Habit;
import model.HabitManager;
import model.DataStorage;

import java.time.LocalDate;

public class MainView extends Application {

    private HabitManager habitManager;
    private HabitController habitController;

    // Constructor to accept HabitManager and HabitController
    public MainView(HabitManager habitManager, HabitController habitController) {
        this.habitManager = habitManager;
        this.habitController = habitController;
    }

    @Override
    public void start(Stage primaryStage) {
        // Layout for the main window
        BorderPane root = new BorderPane();

        // ListView to display habits
        ListView<Habit> habitListView = new ListView<>();
        habitListView.getItems().addAll(habitManager.getHabits());

        // Buttons for habit management
        Button addButton = new Button("Add Habit");
        Button markDoneButton = new Button("Mark Done");
        Button deleteButton = new Button("Delete Habit");
        Button viewAnalyticsButton = new Button("View Analytics");

        // Event handler for adding a new habit
        addButton.setOnAction(e -> showAddHabitDialog(habitListView));

        // Event handler for marking a habit as done
        markDoneButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                habitController.markHabit(selectedHabit.getName(), LocalDate.now());
                updateHabitList(habitListView);
            } else {
                showAlert("No Habit Selected", "Please select a habit to mark as done.");
            }
        });

        // Event handler for deleting a habit
        deleteButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                habitController.removeHabit(selectedHabit.getName());
                updateHabitList(habitListView);
                showAlert("Habit Deleted", "The habit has been successfully deleted.");
            } else {
                showAlert("No Habit Selected", "Please select a habit to delete.");
            }
        });

        // Event handler for viewing analytics
        viewAnalyticsButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                new AnalyticsView(selectedHabit).showAnalytics();
            } else {
                showAlert("No Habit Selected", "Please select a habit to view analytics.");
            }
        });

        // Layout setup for buttons
        VBox buttonBox = new VBox(10, addButton, markDoneButton, deleteButton, viewAnalyticsButton);
        buttonBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        // Set up the root layout
        root.setLeft(buttonBox);
        root.setCenter(habitListView);

        // Scene setup
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Habit Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Save data when the application is closing
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Application is closing. Saving data...");
            DataStorage.saveData(habitManager);
        });
    }

    // Method to show a dialog for adding a new habit
    private void showAddHabitDialog(ListView<Habit> habitListView) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Habit");
        dialog.setHeaderText("Enter a new habit name:");
        dialog.setContentText("Habit Name:");

        dialog.showAndWait().ifPresent(habitName -> {
            habitController.addHabit(habitName, false, 0); // Default measurability and target
            updateHabitList(habitListView);
        });
    }

    // Method to update the habit list view
    private void updateHabitList(ListView<Habit> habitListView) {
        habitListView.getItems().clear();
        habitListView.getItems().addAll(habitManager.getHabits());
    }

    // Method to display an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
