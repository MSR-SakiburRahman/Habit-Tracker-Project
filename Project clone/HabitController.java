package controller;

import model.Habit;
import model.HabitManager;
import java.time.LocalDate;

public class HabitController {

    private HabitManager habitManager;

    public HabitController() {
        // Initialize the HabitManager singleton
        this.habitManager = HabitManager.getInstance();
    }

    // Add a new habit
    public void addHabit(String habitName, boolean isMeasurable, int targetValue) {
        Habit newHabit = new Habit(habitName, isMeasurable, targetValue);
        habitManager.addHabit(newHabit);
        System.out.println("Added new habit: " + habitName);
    }

    // Remove an existing habit
    public void removeHabit(String habitName) {
        Habit habit = habitManager.getHabitByName(habitName);
        if (habit != null) {
            habitManager.removeHabit(habit);
            System.out.println("Removed habit: " + habitName);
        } else {
            System.out.println("Habit not found: " + habitName);
        }
    }

    // Mark a habit as completed for today
    public void markHabit(String habitName, LocalDate date) {
        Habit habit = habitManager.getHabitByName(habitName);
        if (habit != null) {
            habit.markDone(date);
            System.out.println("Marked " + habitName + " as done for " + date);
        } else {
            System.out.println("Habit not found: " + habitName);
        }
    }

    // Get analytics for a specific habit
    public void getAnalytics(String habitName) {
        Habit habit = habitManager.getHabitByName(habitName);
        if (habit != null) {
            System.out.println("Showing analytics for " + habitName);
            // The view component (e.g., `AnalyticsView`) should be invoked here for a GUI
        } else {
            System.out.println("Habit not found: " + habitName);
        }
    }
}
