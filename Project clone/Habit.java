package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a habit in the habit tracker.
 */
public class Habit implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private boolean isMeasurable;
    private int targetValue;
    private List<LocalDate> completionDates;

    // Constructors
    public Habit(String name) {
        this(name, false, 0); // Default: not measurable, no target value
    }

    public Habit(String name, boolean isMeasurable, int targetValue) {
        this.name = name;
        this.isMeasurable = isMeasurable;
        this.targetValue = targetValue;
        this.completionDates = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMeasurable() {
        return isMeasurable;
    }

    public void setMeasurable(boolean isMeasurable) {
        this.isMeasurable = isMeasurable;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int targetValue) {
        this.targetValue = targetValue;
    }

    public List<LocalDate> getCompletionDates() {
        return new ArrayList<>(completionDates); // Return a copy for immutability
    }

    // Mark a habit as completed for a specific date
    public void markDone(LocalDate date) {
        if (!completionDates.contains(date)) {
            completionDates.add(date);
        }
    }

    // Check if a habit is completed on a specific date
    public boolean isCompleted(LocalDate date) {
        return completionDates.contains(date);
    }

    // Helper method to get formatted completion dates
    public List<String> getCompletionDatesString() {
        List<String> formattedDates = new ArrayList<>();
        for (LocalDate date : completionDates) {
            formattedDates.add(date.toString());
        }
        return formattedDates;
    }

    @Override
    public String toString() {
        return String.format("Habit{name='%s', measurable=%s, targetValue=%d}",
                name, isMeasurable, targetValue);
    }
}
