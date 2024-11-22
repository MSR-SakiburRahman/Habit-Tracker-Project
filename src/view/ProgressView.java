package view;

import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Habit;
import model.HabitManager;
import javafx.scene.control.Button;
import controller.HabitController;

import java.util.List;

public class ProgressView {

    private HabitManager habitManager;
    private HabitController habitController;

    // Updated Constructor to accept both HabitManager and HabitController
    public ProgressView(HabitManager habitManager, HabitController habitController) {
        this.habitManager = habitManager;
        this.habitController = habitController;
    }

    public void show(Stage stage) {
        VBox root = new VBox(10);

        // Bar Chart for habit progress
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Habits");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Completion Count");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Habit Progress");

        // Populate the chart with data
        for (Habit habit : habitManager.getHabits()) {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(habit.getName());
            series.getData().add(new XYChart.Data<>(habit.getName(), habit.getCompletionDates().size()));
            barChart.getData().add(series);
        }

        root.getChildren().add(barChart);

        // Back button to return to the main view
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> {
            // Pass both HabitManager and HabitController to the MainView
            MainView mainView = new MainView(habitManager, habitController);
            mainView.start(stage);
        });

        root.getChildren().add(backButton);

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Progress Visualization");
        stage.show();
    }
}
