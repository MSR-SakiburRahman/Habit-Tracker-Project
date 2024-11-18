package main;

import model.Habit;
import model.HabitManager;
import view.MainView;

import javafx.application.Application;

public class Main {

    public static void main(String[] args) {
        // Initialize the HabitManager
        HabitManager habitManager = HabitManager.getInstance();
        habitManager.addHabit(new Habit("Drink Water"));
        habitManager.addHabit(new Habit("Exercise"));

        // Launch the JavaFX application
        Application.launch(MainView.class, args);
    }
}
