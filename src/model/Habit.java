package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class Habit implements Serializable {
    private String name;
    private int goal;
    private int streak;
    private int daysCompleted;
    private Set<LocalDate> completionDates;

    public Habit(String name, int goal) {
        this.name = name;
        this.goal = goal;
        this.streak = 0;
        this.daysCompleted = 0;
        this.completionDates = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getStreak() {
        return streak;
    }

    public int getDaysCompleted() {
        return daysCompleted;
    }

    public double getCompletionPercentage() {
        return goal > 0 ? (daysCompleted / (double) goal) * 100 : 0;
    }

    public void markCompletedToday() {
        LocalDate today = LocalDate.now();
        if (!completionDates.contains(today)) {
            completionDates.add(today);
            daysCompleted++;
            streak++;
        }
    }

    public Set<LocalDate> getCompletionDates() {
        return completionDates;
    }

    public abstract void performAction();

    @Override
    public String toString() {
        return name + " (Goal: " + goal + ")";
    }
}
