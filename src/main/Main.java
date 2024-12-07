package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.HabitTracker;
import view.HabitTrackerGUI;

public class Main extends Application {
    private HabitTrackerGUI gui;

    @Override
    public void start(Stage primaryStage) {
        HabitTracker habitTracker = new HabitTracker();
        gui = new HabitTrackerGUI(habitTracker);
        VBox root = gui.getMainLayout();

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Habit Tracker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        gui.stopReminder();
    }

    public static void main(String[] args) {
        launch(args);
    }
}