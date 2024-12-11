package model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.HabitTrackerGUI;

public class Main extends Application {
    private HabitTrackerGUI habitTrackerGUI;

    @Override
    public void start(Stage stage) {
        HabitTracker habitTracker = new HabitTracker();
        habitTrackerGUI = new HabitTrackerGUI(habitTracker);

        VBox mainLayout = habitTrackerGUI.getMainLayout();

        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setTitle("Habit Tracker");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        habitTrackerGUI.stopReminder();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
