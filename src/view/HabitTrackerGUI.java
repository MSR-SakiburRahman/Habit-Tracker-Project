package view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import model.CustomException;
import model.Habit;
import model.HabitTracker;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class HabitTrackerGUI {
    private VBox mainLayout;
    private HabitTracker habitTracker;
    private ListView<Habit> habitList;
    private Label habitDetails;
    private Timer reminderTimer;

    public HabitTrackerGUI(HabitTracker habitTracker) {
        this.habitTracker = habitTracker;
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        setupUI();

        // Load habits on startup
        try {
            habitTracker.loadFromFile();
            refreshHabitList();
            showAlert("Success", "Habits loaded successfully!");
        } catch (CustomException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void setupUI() {
        // Title
        Text title = new Text("Habit Tracker");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Habit list
        habitList = new ListView<>();
        habitList.getItems().addAll(habitTracker.getHabits());
        habitList.getSelectionModel().selectedItemProperty().addListener((obs, oldHabit, newHabit) -> {
            if (newHabit != null) {
                updateHabitDetails(newHabit);
            }
        });

        // Habit details area
        habitDetails = new Label("Select a habit to view details");
        habitDetails.setWrapText(true);
        habitDetails.setStyle("-fx-border-color: gray; -fx-padding: 10;");

        // Input field for adding a habit
        TextField habitInput = new TextField();
        habitInput.setPromptText("Enter a new habit");

        // Buttons
        Button addButton = new Button("Add Habit");
        addButton.setOnAction(e -> {
            String habitName = habitInput.getText().trim();
            if (!habitName.isEmpty()) {
                Habit habit = new Habit(habitName);
                habitTracker.addHabit(habit);
                refreshHabitList();
                habitInput.clear();
            }
        });

        Button toggleButton = new Button("Toggle Completed");
        toggleButton.setOnAction(e -> {
            Habit selectedHabit = habitList.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                selectedHabit.toggleCompleted();
                updateHabitDetails(selectedHabit);
                refreshHabitList();
            }
        });

        Button viewCompletionDatesButton = new Button("View Completion Dates");
        viewCompletionDatesButton.setOnAction(e -> {
            Habit selectedHabit = habitList.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                showCompletionDatesDialog(selectedHabit);
            }
        });

        Button editButton = new Button("Edit Habit");
        editButton.setOnAction(e -> {
            Habit selectedHabit = habitList.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                showEditHabitDialog(selectedHabit);
                refreshHabitList();
            }
        });

        Button deleteButton = new Button("Delete Habit");
        deleteButton.setOnAction(e -> {
            Habit selectedHabit = habitList.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                habitTracker.removeHabit(selectedHabit);
                refreshHabitList();
                habitDetails.setText("Select a habit to view details");
            }
        });

        Button saveButton = new Button("Save Habits");
        saveButton.setOnAction(e -> {
            try {
                habitTracker.saveToFile();
                showAlert("Success", "Habits saved successfully!");
            } catch (CustomException ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        // Layout
        HBox buttonBar = new HBox(10, addButton, toggleButton, viewCompletionDatesButton, editButton, deleteButton, saveButton);
        buttonBar.setPadding(new Insets(10, 0, 0, 0));

        mainLayout.getChildren().addAll(title, habitList, habitDetails, habitInput, buttonBar);

        // Add a daily reminder
        setupReminder();
    }

    private void refreshHabitList() {
        habitList.getItems().clear();
        habitList.getItems().addAll(habitTracker.getHabits());
    }

    private void updateHabitDetails(Habit habit) {
        habitDetails.setText(
                "Habit: " + habit.getName() + "\n" +
                        "Completed Today: " + (habit.isCompletedToday() ? "Yes" : "No") + "\n" +
                        "Current Streak: " + habit.getCurrentStreak() + " days\n" +
                        "Best Streak: " + habit.getBestStreak() + " days\n" +
                        "Completion Dates: " + habit.getCompletionDates()
        );
    }

    private void showEditHabitDialog(Habit habit) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Habit");

        TextField nameField = new TextField(habit.getName());
        VBox content = new VBox(10, new Label("Habit Name:"), nameField);
        dialog.getDialogPane().setContent(content);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return nameField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> habit.setName(newName));
    }

    private void showCompletionDatesDialog(Habit habit) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Completion Dates");
        alert.setHeaderText("Completion Dates for " + habit.getName());
        alert.setContentText(String.join(", ", habit.getCompletionDates().toString()));
        alert.showAndWait();
    }

    private void setupReminder() {
        reminderTimer = new Timer();
        reminderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> showAlert("Reminder", "Don't forget to complete your habits!"));
            }
        }, 0, 24 * 60 * 60 * 1000); // Reminder every 24 hours
    }

    public VBox getMainLayout() {
        return mainLayout;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void stopReminder() {
        if (reminderTimer != null) {
            reminderTimer.cancel();
        }
    }
}