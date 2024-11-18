package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habit;
import model.HabitManager;

public class HabitManagementView {

    private HabitManager habitManager;

    public HabitManagementView(HabitManager habitManager) {
        this.habitManager = habitManager;
    }

    public void show(Stage stage) {
        VBox root = new VBox(10);

        ListView<Habit> habitListView = new ListView<>();
        habitListView.getItems().addAll(habitManager.getHabits());

        TextField habitNameField = new TextField();
        habitNameField.setPromptText("Enter habit name");

        Button addButton = new Button("Add Habit");
        addButton.setOnAction(e -> {
            String habitName = habitNameField.getText();
            if (!habitName.isEmpty()) {
                Habit newHabit = new Habit(habitName);
                habitManager.addHabit(newHabit);
                habitListView.getItems().add(newHabit);
                habitNameField.clear();
            }
        });

        Button removeButton = new Button("Remove Selected Habit");
        removeButton.setOnAction(e -> {
            Habit selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            if (selectedHabit != null) {
                habitManager.removeHabit(selectedHabit);
                habitListView.getItems().remove(selectedHabit);
            }
        });

        root.getChildren().addAll(habitNameField, addButton, removeButton, habitListView);

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Manage Habits");
        stage.show();
    }
}
