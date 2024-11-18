package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles saving and loading habit data to and from a file.
 */
public class DataStorage {

    private static final String FILE_PATH = "habits_data.ser"; // File path for storing habits

    /**
     * Saves the list of habits to a file using serialization.
     *
     * @param habits List of Habit objects to be saved.
     */
    public static void saveData(List<Habit> habits) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(habits);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Loads the list of habits from a file using deserialization.
     *
     * @return List of Habit objects. Returns an empty list if the file does not exist or an error occurs.
     */
    public static List<Habit> loadData() {
        File file = new File(FILE_PATH);

        // Return an empty list if the file does not exist
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            @SuppressWarnings("unchecked") // Suppress warnings for unchecked cast
            List<Habit> habits = (List<Habit>) ois.readObject();
            return habits;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list on error
        }
    }
}
