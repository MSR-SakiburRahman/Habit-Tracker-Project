package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Habit {
    private String name;
    private boolean completedToday;
    private Set<LocalDate> completionDates;
    private int currentStreak;
    private int bestStreak;

    public Habit(String name) {
        this.name = name;
        this.completedToday = false;
        this.completionDates = new TreeSet<>();
        this.currentStreak = 0;
        this.bestStreak = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompletedToday() {
        return completedToday;
    }

    public void toggleCompleted() {
        LocalDate today = LocalDate.now();
        if (completedToday) {
            completionDates.remove(today);
        } else {
            completionDates.add(today);
        }
        completedToday = !completedToday;
        updateStreaks();
    }

    private void updateStreaks() {
        LocalDate today = LocalDate.now();
        int streak = 0;

        while (completionDates.contains(today.minusDays(streak))) {
            streak++;
        }

        currentStreak = streak;
        bestStreak = Math.max(bestStreak, currentStreak);
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getBestStreak() {
        return bestStreak;
    }

    public Set<LocalDate> getCompletionDates() {
        return completionDates;
    }

    // Serialize completion dates to a string
    public String getCompletionDatesAsString() {
        StringBuilder sb = new StringBuilder();
        for (LocalDate date : completionDates) {
            sb.append(date).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // Remove trailing comma
        }
        return sb.toString();
    }

    // Deserialize a string into completion dates
    public static Set<LocalDate> parseCompletionDates(String datesString) {
        Set<LocalDate> dates = new HashSet<>();
        if (datesString != null && !datesString.isEmpty()) {
            String[] dateStrings = datesString.split(",");
            for (String dateString : dateStrings) {
                dates.add(LocalDate.parse(dateString));
            }
        }
        return dates;
    }

    @Override
    public String toString() {
        return name + (completedToday ? " (Completed)" : " (Not Completed)");
    }
}