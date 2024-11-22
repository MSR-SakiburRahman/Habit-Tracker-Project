package model;

import java.io.*;
import java.nio.file.Paths;

public class DataStorage {

    // File name for habit data
    private static final String FILE_NAME = "Data_Storage_Of_Habits.ser";

    // Method to save the HabitManager instance to a file
    public static void saveData(HabitManager habitManager) {
        File file = getDefaultFile();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(habitManager);
            System.out.println("Habit data saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving habit data: " + e.getMessage());
        }
    }

    // Method to load the HabitManager instance from a file
    public static HabitManager loadData() {
        File file = getDefaultFile();
        if (!file.exists()) {
            System.out.println("No existing data found. Starting with a new HabitManager instance.");
            return HabitManager.getInstance();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            HabitManager loadedManager = (HabitManager) ois.readObject();
            HabitManager.replaceInstance(loadedManager);
            System.out.println("Habit data loaded from: " + file.getAbsolutePath());
            return loadedManager;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading habit data: " + e.getMessage());
            return HabitManager.getInstance();
        }
    }

    // Utility method to get the default file path for habit data
    private static File getDefaultFile() {
        // Get the current project directory
        String projectDir = System.getProperty("user.dir"); // Returns the working directory of the application
        File defaultDir = Paths.get(projectDir, "data").toFile(); // Create a "data" subdirectory in the project
        if (!defaultDir.exists()) {
            defaultDir.mkdirs(); // Create directory if it doesn't exist
        }
        return new File(defaultDir, FILE_NAME); // Return the full path to the data file
    }
}
