import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() {
        File file = new File(filePath);
        ArrayList<Task> loadedTasks = new ArrayList<>();
        if (!file.exists()) {
            return loadedTasks;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                Task task = parseTaskFromFileString(line);
                if (task != null) {
                    loadedTasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, will be created on first save.");
        } catch (MonetException e) {
            System.out.println("Error parsing file: " + e.getMessage() + ". Starting with an empty task list.");
            return new ArrayList<>();
        }
        return loadedTasks;
    }

    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);
        for (Task task : tasks) {
            fw.write(task.toFileString() + System.lineSeparator());
        }
        fw.close();
    }

    private Task parseTaskFromFileString(String line) throws MonetException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            System.out.println("Warning: Corrupted line in data file will be ignored: " + line);
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task;

        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                // Add specific validation for deadline format
                if (parts.length < 4) {
                    System.out.println("Warning: Corrupted deadline task in data file will be ignored: " + line);
                    return null;
                }
                LocalDateTime by = LocalDateTime.parse(parts[3]);
                task = new Deadline(description, by);
                break;
            case "E":
                // Add specific validation for event format
                if (parts.length < 5) {
                    System.out.println("Warning: Corrupted event task in data file will be ignored: " + line);
                    return null;
                }
                LocalDateTime from = LocalDateTime.parse(parts[3]);
                LocalDateTime to = LocalDateTime.parse(parts[4]);
                task = new Event(description, from, to);
                break;
            default:
                // If the type is unknown, it's also a corrupted line.
                System.out.println("Warning: Unknown task type in data file will be ignored: " + line);
                return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}