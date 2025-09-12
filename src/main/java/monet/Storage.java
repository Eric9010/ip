package monet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading tasks from and saving tasks to a file.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object.
     *
     * @param filePath The path of the file to be used for storing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws MonetException if the file contains corrupted data.
     */
    public ArrayList<Task> load() throws MonetException {
        // Create a File object from the given path to interact with the file system.
        File file = new File(filePath);
        ArrayList<Task> loadedTasks = new ArrayList<>();
        if (!file.exists()) {
            return loadedTasks; // Return empty list if no file exists.
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) { // Skip any empty lines that might be in the file.
                    continue;
                }
                // Parse the line and add the resulting Task object to the list.
                Task task = parseTaskFromFileString(line);
                if (task != null) {
                    loadedTasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            // Catch cases where the file is not found, returns an empty list instead.
            System.out.println("File not found, will be created on first save.");
        }
        return loadedTasks;
    }

    /**
     * Saves the current list of tasks to the storage file.
     *
     * @param tasks The ArrayList of tasks to save.
     * @throws IOException If there is an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);

        // Ensures that the parent directory (e.g., "./data") exists.
        // If it doesn't, it will be created. Prevents crashes on the first run.
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);
        for (Task task : tasks) {
            // Convert each task to its machine-readable file format.
            fw.write(task.toFileString() + System.lineSeparator());
        }
        fw.close();
    }

    /**
     * Parses a single line from the file into a Task object.
     * This is a private helper method for the load() function.
     *
     * @param line The string line from the file.
     * @return The corresponding Task object, or null if the line is corrupted.
     * @throws MonetException if the line contains a known but malformed task.
     */
    private Task parseTaskFromFileString(String line) throws MonetException {
        // Format: T | 0 | MEDIUM | read book
        String[] parts = line.split(" \\| ");
        String warning = "Warning: Corrupted line in data file will be ignored: ";

        if (parts.length < 4) {
            System.out.println(warning + line);
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        Priority priority = Priority.valueOf(parts[2]); // Convert string back to enum
        String description = parts[3];
        Task task;

        // Use assert to validate the acceptable task types
        assert type.equals("T") || type.equals("D") || type.equals("E") : "Unknown task type in file";

        switch (type) {
        case "T":
            task = new Todo(description, priority);
            break;
        case "D":
            // Add specific validation for deadline format.
            if (parts.length < 5) {
                System.out.println(warning + line);
                return null;
            }
            LocalDateTime by = LocalDateTime.parse(parts[4]);
            task = new Deadline(description, by, priority);
            break;
        case "E":
            // Add specific validation for event format.
            if (parts.length < 6) {
                System.out.println(warning + line);
                return null;
            }
            LocalDateTime from = LocalDateTime.parse(parts[4]);
            LocalDateTime to = LocalDateTime.parse(parts[5]);
            task = new Event(description, from, to, priority);
            break;
        default:
            // If the type is unknown, it's also a corrupted line.
            System.out.println(warning + line);
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
