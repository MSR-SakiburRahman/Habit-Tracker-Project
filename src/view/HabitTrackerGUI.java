package view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.*;

import java.util.Timer;
import java.util.TimerTask;

public class HabitTrackerGUI {
    private VBox mainLayout;
    private HabitTracker habitTracker;
    private ListView<Habit> habitListView;
    private Timer reminderTimer;

    public HabitTrackerGUI(HabitTracker habitTracker) {
        this.habitTracker = habitTracker;
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        setupUI();
        try {
            habitTracker.loadHabits();
            refreshHabitList();
        } catch (CustomException e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void setupUI() {
        Label title = new Label("Habit Tracker");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        habitListView = new ListView<>();

        TextField habitInputField = new TextField();
        habitInputField.setPromptText("Enter habit name");

        TextField goalInputField = new TextField();
        goalInputField.setPromptText("Enter habit goal");

        Button addButton = new Button("Add Habit");
        addButton.setOnAction(e -> {
            String habitName = habitInputField.getText().trim();
            String goalText = goalInputField.getText().trim();
            if (!habitName.isEmpty() && !goalText.isEmpty()) {
                try {
                    int goal = Integer.parseInt(goalText);
                    Habit newHabit = new SimpleHabit(habitName, goal);
                    habitTracker.addHabit(newHabit);
                    refreshHabitList();
                    habitInputField.clear();
                    goalInputField.clear();
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Goal must be a number.");
                }
            }
        });

        Button deleteButton = new Button("Delete Habit");
        deleteButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                try {
                    habitTracker.deleteHabit(selectedHabit);
                    refreshHabitList();
                } catch (CustomException ex) {
                    showAlert("Error", ex.getMessage());
                }
            }
        });

        Button editButton = new Button("Edit Habit");
        editButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                TextInputDialog nameDialog = new TextInputDialog(selectedHabit.getName());
                nameDialog.setHeaderText("Edit Habit Name");
                nameDialog.setContentText("New Name:");

                TextInputDialog goalDialog = new TextInputDialog(String.valueOf(selectedHabit.getGoal()));
                goalDialog.setHeaderText("Edit Habit Goal");
                goalDialog.setContentText("New Goal:");

                nameDialog.showAndWait().ifPresent(newName -> {
                    goalDialog.showAndWait().ifPresent(newGoal -> {
                        try {
                            int goal = Integer.parseInt(newGoal);
                            habitTracker.editHabit(selectedHabit, newName, goal);
                            refreshHabitList();
                        } catch (NumberFormatException ex) {
                            showAlert("Error", "Goal must be a number.");
                        }
                    });
                });
            }
        });

        Button statsButton = new Button("Show Stats");
        statsButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                showAlert(
                        "Habit Stats",
                        "Name: " + selectedHabit.getName() + "\n" +
                                "Goal: " + selectedHabit.getGoal() + "\n" +
                                "Days Completed: " + selectedHabit.getDaysCompleted() + "\n" +
                                "Current Streak: " + selectedHabit.getStreak() + "\n" +
                                "Completion %: " + String.format("%.2f", selectedHabit.getCompletionPercentage()) + "%"
                );
            }
        });

        Button markCompleteButton = new Button("Mark as Complete");
        markCompleteButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                selectedHabit.markCompletedToday();
                showAlert("Success", "Marked habit as completed for today!");
                refreshHabitList();
            }
        });

        HBox inputBox = new HBox(10, habitInputField, goalInputField, addButton);
        HBox actionBox = new HBox(10, deleteButton, editButton, statsButton, markCompleteButton);

        mainLayout.getChildren().addAll(title, habitListView, inputBox, actionBox);

        setupReminder();
    }

    private void refreshHabitList() {
        habitListView.getItems().clear();
        habitListView.getItems().addAll(habitTracker.getHabits());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setupReminder() {
        reminderTimer = new Timer();
        reminderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> showAlert("Reminder", "Don't forget to complete your habits!"));
            }
        }, 0, 24 * 60 * 60 * 1000);
    }

    public VBox getMainLayout() {
        return mainLayout;
    }

    public void stopReminder() {
        if (reminderTimer != null) {
            reminderTimer.cancel();
        }
    }
}
