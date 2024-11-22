package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading habit data to and from a file.
 */
public class DataStorage {

    private static final String FILE_PATH = "D:\\Codes\\Java IntelliJ\\Experiment Project Habit Tracker\\src\\model\\HabitDataStorage.ser"; // File path for storing habits

    /**
     * Saves the HabitManager instance to a file using serialization.
     *
     * @param habitManager The HabitManager instance to save.
     */
    public static void saveData(HabitManager habitManager) {
        File file = new File(FILE_PATH);

        // Ensure parent directories exist
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            System.err.println("Failed to create directories for file: " + file.getAbsolutePath());
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(habitManager);
            System.out.println("Data successfully saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads the HabitManager instance from a file using deserialization.
     *
     * @return The deserialized HabitManager instance. Returns a new HabitManager if the file does not exist or an error occurs.
     */
    public static HabitManager loadData() {
        File file = new File(FILE_PATH);

        // Debug: Print the file's absolute path
        System.out.println("Loading data from: " + file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("File not found. Returning a new HabitManager.");
            return HabitManager.getInstance(); // Return a new HabitManager if file doesn't exist
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            HabitManager habitManager = (HabitManager) ois.readObject();

            // Replace the singleton instance with the loaded one
            HabitManager.replaceInstance(habitManager);

            System.out.println("Data successfully loaded!");
            return habitManager;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            return HabitManager.getInstance(); // Return a new HabitManager on error
        }
    }
}
