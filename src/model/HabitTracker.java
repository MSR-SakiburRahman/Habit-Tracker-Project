package model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HabitTracker {
    private List<Habit> habits;
    private static final String FILE_NAME = "habits.txt";

    public HabitTracker() {
        habits = new ArrayList<>();
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public void removeHabit(Habit habit) {
        habits.remove(habit);
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void saveToFile() throws CustomException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Habit habit : habits) {
                writer.write(habit.getName() + "|" + habit.isCompletedToday() + "|" + habit.getCurrentStreak() + "|" + habit.getBestStreak() + "|" + habit.getCompletionDatesAsString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new CustomException("Failed to save habits: " + e.getMessage());
        }
    }

    public void loadFromFile() throws CustomException {
        habits.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String name = parts[0];
                    boolean completedToday = Boolean.parseBoolean(parts[1]);
                    int currentStreak = Integer.parseInt(parts[2]);
                    int bestStreak = Integer.parseInt(parts[3]);
                    Set<LocalDate> completionDates = Habit.parseCompletionDates(parts[4]);

                    Habit habit = new Habit(name);
                    habit.toggleCompleted();
                    habits.add(habit);
                }
            }
        } catch (IOException e) {
            throw new CustomException("Failed to load habits: " + e.getMessage());
        }
    }
}