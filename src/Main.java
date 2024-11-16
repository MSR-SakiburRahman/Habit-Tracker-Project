import java.util.Scanner;

public class CreateHabit {
    private String[] habit;  // Store habit descriptions
    private String[][] dailyCompletion;  // 2D array to track completion (tick/cross) for each habit and day
    private int numOfHabit;  // Number of habits
    private final int daysInMonth = 30;  // Assume 30 days in the month (can adjust to 31 or 28/29)

    // Constructor to initialize habits and daily completion tracker
    public CreateHabit(int numOfHabit) {
        this.numOfHabit = numOfHabit;
        this.habit = new String[numOfHabit];  // Habit descriptions
        this.dailyCompletion = new String[daysInMonth][numOfHabit];  // Track completion for each day and each habit
    }

    // Method to set the description of a habit
    public void setHabit(int index, String habitDescription) {
        if (index >= 0 && index < numOfHabit) {
            habit[index] = habitDescription;
        }
    }

    // Method to get the description of a habit
    public String getHabit(int index) {
        if (index >= 0 && index < numOfHabit) {
            return habit[index];
        }
        return null;  // Or throw an exception
    }

    // Mark the completion (tick or cross) for a habit on a specific day
    public void markCompletion(int day, int habitIndex, String completionStatus) {
        if (habitIndex >= 0 && habitIndex < numOfHabit && day >= 0 && day < daysInMonth) {
            dailyCompletion[day][habitIndex] = completionStatus;  // "✓" for tick, "✗" for cross
        }
    }

    // Display the completion status for all habits over the entire month (days in rows, habits in columns)
    public void displayHabitProgress() {
        System.out.println("Habit Completion for the Month (Rows: Days, Columns: Habits)");

        // Print header for habits (columns)
        System.out.print(String.format("%-10s", "Day"));  // Use String.format to pad "Day" column header
        for (int i = 0; i < numOfHabit; i++) {
            System.out.print(String.format("%-20s", habit[i]));  // Use String.format for habit descriptions
        }
        System.out.println();  // Newline

        // Print progress for each day (rows)
        for (int day = 0; day < daysInMonth; day++) {
            System.out.print(String.format("%-10s", "Day " + (day + 1)));  // Format day label
            for (int i = 0; i < numOfHabit; i++) {
                String status = dailyCompletion[day][i] != null ? dailyCompletion[day][i] : " ";  // Default empty if not set
                System.out.print(String.format("%-20s", status));  // Format completion status
            }
            System.out.println();  // Newline after each day
        }
    }

    // Main method to test the functionality
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for the number of habits
        System.out.print("Enter the number of habits: ");
        int numOfHabits = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        CreateHabit habitTracker = new CreateHabit(numOfHabits);  // Create habit tracker

        // Prompt user to input habit descriptions
        for (int i = 0; i < numOfHabits; i++) {
            System.out.print("Enter description for Habit " + (i + 1) + ": ");
            String habitDescription = scanner.nextLine();
            habitTracker.setHabit(i, habitDescription);
        }

        // Allow the user to input completion status for specific days
        while (true) {
            System.out.print("Enter a day (1-30) to update (or 0 to finish): ");
            int day = scanner.nextInt();
            if (day == 0) {
                break;  // Exit loop when the user enters 0
            }
            if (day < 1 || day > 30) {
                System.out.println("Invalid day! Please enter a day between 1 and 30.");
                continue;
            }
            scanner.nextLine();  // Consume the newline character

            // For the selected day, ask user to enter the completion status for each habit
            for (int i = 0; i < numOfHabits; i++) {
                System.out.print("Habit '" + habitTracker.getHabit(i) + "' (tick/✓ or cross/✗): ");
                String status = scanner.nextLine().trim();
                while (!status.equals("✓") && !status.equals("✗")) {
                    System.out.print("Invalid input! Please enter tick/✓ or cross/✗: ");
                    status = scanner.nextLine().trim();
                }
                habitTracker.markCompletion(day - 1, i, status);  // Mark completion for the selected day
            }
        }

        // Displaying habit progress for the entire month
        habitTracker.displayHabitProgress();

        scanner.close();  // Close the scanner
    }
}
