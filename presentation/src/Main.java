import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private List<Habit> habits = new ArrayList<>();
    private ListView<String> habitListView = new ListView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loadHabits();

        VBox root = new VBox(10);
        TextField habitInput = new TextField();
        habitInput.setPromptText("Enter habit name");

        Button addHabitButton = new Button("Add Habit");
        Button deleteHabitButton = new Button("Delete Habit");
        Button markDoneButton = new Button("Mark Done");
        Button markNotDoneButton = new Button("Mark Not Done");
        Button viewSummaryButton = new Button("View Summary");

        root.getChildren().addAll(habitInput, addHabitButton, deleteHabitButton, markDoneButton, markNotDoneButton, viewSummaryButton, habitListView);
        updateHabitListView();

        addHabitButton.setOnAction(e -> {
            String habitName = habitInput.getText();
            if (!habitName.isEmpty()) {
                habits.add(new DailyHabit(habitName));
                updateHabitListView();
                habitInput.clear();
            }
        });

        deleteHabitButton.setOnAction(e -> {
            String selectedHabit = habitListView.getSelectionModel().getSelectedItem();
            try {
                deleteHabit(selectedHabit);
                updateHabitListView();
            } catch (HabitNotFoundException ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        markDoneButton.setOnAction(e -> markHabit(true));
        markNotDoneButton.setOnAction(e -> markHabit(false));

        viewSummaryButton.setOnAction(e -> {
            String summary = "";
            for (int i = 0; i < habits.size(); i++) {
                Habit habit = habits.get(i);
                summary += habit.getName() + ": " + habit.getDaysDone() + " days done\n";
            }
            showAlert("Habit Summary", summary);
        });

        primaryStage.setTitle("Habit Tracker");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.setOnCloseRequest(e -> saveHabits());
        primaryStage.show();
    }

    private void updateHabitListView() {
        habitListView.getItems().clear();
        for (int i = 0; i < habits.size(); i++) {
            habitListView.getItems().add(habits.get(i).getName());
        }
    }

    private void deleteHabit(String habitName) throws HabitNotFoundException {
        boolean found = false;
        for (int i = 0; i < habits.size(); i++) {
            if (habits.get(i).getName().equals(habitName)) {
                habits.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new HabitNotFoundException("Habit not found: " + habitName);
        }
    }

    private void markHabit(boolean done) {
        String selectedHabit = habitListView.getSelectionModel().getSelectedItem();
        for (int i = 0; i < habits.size(); i++) {
            if (habits.get(i).getName().equals(selectedHabit)) {
                habits.get(i).markDay(done);
                return;
            }
        }
        showAlert("Error", "Please select a habit first.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveHabits() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("habits.txt"))) {
            for (int i = 0; i < habits.size(); i++) {
                Habit habit = habits.get(i);
                writer.write(habit.getName() + "," + habit.getDaysDone() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHabits() {
        try (BufferedReader reader = new BufferedReader(new FileReader("habits.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                DailyHabit habit = new DailyHabit(parts[0]);
                habits.add(habit);
            }
        } catch (IOException e) {
            habits = new ArrayList<>();
        }
    }
}
