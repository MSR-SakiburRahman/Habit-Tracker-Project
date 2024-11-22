package main;

import model.DataStorage;
import model.HabitManager;
import view.MainView;
import controller.HabitController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the HabitManager data
        HabitManager habitManager = DataStorage.loadData();

        // Create the HabitController
        HabitController habitController = new HabitController();

        // Pass the HabitManager and HabitController to the MainView
        MainView mainView = new MainView(habitManager, habitController);
        mainView.start(primaryStage);

        // Save data when the application is closing
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Application is closing. Saving data...");
            DataStorage.saveData(habitManager);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
