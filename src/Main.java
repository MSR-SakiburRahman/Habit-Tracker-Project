import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask the user for the number of habits
        System.out.print("Enter the number of habits: ");
        int numOfHabits = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        CreateHabit habitTracker = new CreateHabit(numOfHabits);

        // Input habit descriptions
        for (int i = 0; i < numOfHabits; i++) {
            System.out.print("Enter description for Habit " + (i + 1) + ": ");
            String habitDescription = scanner.nextLine();
            habitTracker.setHabit(i, habitDescription);
        }

        // Allow the user to update completion status for any day
        while (true) {
            System.out.print("Enter a day (1-30) to update (or 0 to finish): ");
            int day = scanner.nextInt();
            if (day == 0) {
                break;
            }
            if (day < 1 || day > 30) {
                System.out.println("Invalid day! Please enter a day between 1 and 30.");
                continue;
            }
            scanner.nextLine();  // Consume the newline character

            // Update completion status for each habit on the selected day
            for (int i = 0; i < numOfHabits; i++) {
                System.out.print("Habit '" + habitTracker.getHabit(i) + "' (type 't' for tick/✓ or 'x' for cross/✗): ");
                String input = scanner.nextLine().trim().toLowerCase();

                // Convert input to tick or cross
                String status;
                if (input.equals("t")) {
                    status = "✓";
                } else if (input.equals("x")) {
                    status = "✗";
                } else {
                    System.out.println("Invalid input! Defaulting to empty status.");
                    status = " ";
                }

                habitTracker.markCompletion(day - 1, i, status);
            }
        }

        // Display habit progress for the entire month
        habitTracker.displayHabitProgress();

        scanner.close();
    }
}
