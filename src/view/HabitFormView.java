package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habit;
import model.HabitManager;

public class HabitFormView {

    private Habit habit; // The habit to be created or edited
    private HabitManager habitManager;

    public HabitFormView(HabitManager habitManager, Habit habit) {
        this.habitManager = habitManager;
        this.habit = habit;
    }

    public void showForm() {
        // Create a new stage for the form
        Stage formStage = new Stage();
        formStage.setTitle(habit == null ? "New Habit" : "Edit Habit");

        // Layout for the form
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-spacing: 10;");

        // TextField for habit name
        TextField habitNameField = new TextField();
        habitNameField.setPromptText("Habit Name");
        if (habit != null) {
            habitNameField.setText(habit.getName());
        }

        // Checkbox for whether the habit is measurable
        CheckBox measurableCheckBox = new CheckBox("Measurable");
        if (habit != null && habit.isMeasurable()) {
            measurableCheckBox.setSelected(true);
        }

        // TextField for the target value (if measurable)
        TextField targetValueField = new TextField();
        targetValueField.setPromptText("Target Value (Optional)");
        targetValueField.setDisable(true);

        // Enable or disable target value field based on measurable checkbox
        measurableCheckBox.setOnAction(e -> targetValueField.setDisable(!measurableCheckBox.isSelected()));

        // Pre-fill target value if habit is being edited
        if (habit != null && habit.isMeasurable()) {
            targetValueField.setText(String.valueOf(habit.getTargetValue()));
            targetValueField.setDisable(false);
        }

        // Save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String habitName = habitNameField.getText();
            boolean isMeasurable = measurableCheckBox.isSelected();
            int targetValue = 0;

            // Parse the target value if measurable is checked
            if (isMeasurable) {
                try {
                    targetValue = Integer.parseInt(targetValueField.getText());
                } catch (NumberFormatException ex) {
                    showError("Invalid target value. Please enter a valid number.");
                    return;
                }
            }

            if (habitName.isEmpty()) {
                showError("Habit name cannot be empty.");
                return;
            }

            // If editing an existing habit
            if (habit != null) {
                habit.setName(habitName);
                habit.setMeasurable(isMeasurable);
                habit.setTargetValue(targetValue);
            } else {
                // Create a new habit
                Habit newHabit = new Habit(habitName);
                newHabit.setMeasurable(isMeasurable);
                newHabit.setTargetValue(targetValue);
                habitManager.addHabit(newHabit);
            }

            formStage.close();
        });

        // Add components to layout
        layout.getChildren().addAll(habitNameField, measurableCheckBox, targetValueField, saveButton);

        // Scene setup
        Scene scene = new Scene(layout, 400, 300);
        formStage.setScene(scene);
        formStage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
