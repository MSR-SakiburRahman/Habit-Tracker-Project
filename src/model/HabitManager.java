package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HabitManager implements Serializable {

    private static final long serialVersionUID = 1L;
    // Singleton instance
    private static HabitManager instance;

    // List to store all habits
    private List<Habit> habits;

    // Private constructor to prevent external instantiation
    private HabitManager() {
        this.habits = new ArrayList<>();
    }

    // Singleton instance retrieval method
    public static HabitManager getInstance() {
        if (instance == null) {
            instance = new HabitManager();
        }
        return instance;
    }

    // Method to add a new habit
    public void addHabit(Habit habit) {
        if (habit != null && !habits.contains(habit)) {
            habits.add(habit);
        }
    }

    // Method to remove an existing habit
    public void removeHabit(Habit habit) {
        habits.remove(habit);
    }

    // Method to retrieve a habit by its name
    public Habit getHabitByName(String name) {
        for (Habit habit : habits) {
            if (habit.getName().equalsIgnoreCase(name)) {
                return habit;
            }
        }
        return null; // Return null if not found
    }

    // Method to retrieve all habits
    public List<Habit> getHabits() {
        return new ArrayList<>(habits); // Return a copy to preserve encapsulation
    }

    // Method to clear all habits (for reset/testing)
    public void clearHabits() {
        habits.clear();
    }

    public static void replaceInstance(HabitManager newInstance) {
        instance = newInstance;
    }

}
