package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HabitTracker {
    private List<Habit> habits;
    private static final String FILE_NAME = "habits.dat";

    public HabitTracker() {
        habits = new ArrayList<>();
        try {
            loadHabits();
        } catch (CustomException e) {
            System.err.println("No previous habits found. Starting fresh.");
        }
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
        try {
            saveHabits();
        } catch (CustomException e) {
            System.err.println("Error saving habits: " + e.getMessage());
        }
    }

    public void deleteHabit(Habit habit) throws CustomException {
        if (!habits.remove(habit)) {
            throw new CustomException("Habit not found!");
        }
        saveHabits();
    }

    public void editHabit(Habit habit, String newName, int newGoal) {
        habit.setName(newName);
        habit.setGoal(newGoal);
        try {
            saveHabits();
        } catch (CustomException e) {
            System.err.println("Error saving habits: " + e.getMessage());
        }
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void saveHabits() throws CustomException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(habits);
        } catch (IOException e) {
            throw new CustomException("Error saving habits: " + e.getMessage());
        }
    }

    public void loadHabits() throws CustomException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            habits = (List<Habit>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found means no data yet. Not an error.
            habits = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new CustomException("Error loading habits: " + e.getMessage());
        }
    }
}
