import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Monet {
    /** Static fields for file handing */
    private static final String FILE_PATH = "./data/monet.txt";
    private static ArrayList<Task> tasks;

    public static void main(String[] args) {
        tasks = loadFile(); // Load tasks from the file at startup.

        String chatbotName = "Monet";
        String divider = "____________________________________________________________";

        System.out.println(" Hello! I'm " + chatbotName);
        System.out.println(" What can I do for you?");
        System.out.println(divider);

        Scanner in = new Scanner(System.in);

        while (true) {
            try {
                String input = in.nextLine();
                Command command = Parser.parseCommand(input);

                if (command == Command.BYE) {
                    break;
                }

                switch (command) {
                case LIST:
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("  " + (i + 1) + "." + tasks.get(i));
                    }
                    break;

                case MARK:
                case UNMARK: {
                    int taskIndex = Parser.parseIndex(input, tasks.size());
                    Task task = tasks.get(taskIndex);
                    if (command == Command.MARK) {
                        task.markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                    } else {
                        task.unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                    }
                    System.out.println("   " + task);
                    saveFile(tasks); // Save changes to the file
                    break;
                }

                case DELETE: {
                    int taskIndex = Parser.parseIndex(input, tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    System.out.println(" Noted. I've removed this task:");
                    System.out.println("   " + removedTask);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    saveFile(tasks); // Save changes to the file
                    break;
                }

                case TODO: {
                    String description = Parser.parseTodo(input);
                    Todo newTodo = new Todo(description);
                    tasks.add(newTodo);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newTodo);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    saveFile(tasks); // Save changes to the file
                    break;
                }

                case DEADLINE: {
                    String[] deadlineDetails = Parser.parseDeadline(input);
                    Deadline newDeadline = new Deadline(deadlineDetails[0], deadlineDetails[1]);
                    tasks.add(newDeadline);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newDeadline);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    saveFile(tasks); // Save changes to the file
                    break;
                }

                case EVENT: {
                    String[] eventDetails = Parser.parseEvent(input);
                    Event newEvent = new Event(eventDetails[0], eventDetails[1], eventDetails[2]);
                    tasks.add(newEvent);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newEvent);
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    saveFile(tasks); // Save changes to the file
                    break;
                }

                case UNKNOWN:
                default:
                    throw new MonetException("I don't know what that means. Please check your input!");
                }

            } catch (MonetException | IOException e) {
                System.out.println("Sorry! " + e.getMessage());
            } finally {
                System.out.println(divider);
            }
        }
        System.out.println(" Bye. Hope to see you again soon!");
    }

    /**
     *
     *
     * @param tasks
     * @throws IOException
     */

    private static void saveFile(ArrayList<Task> tasks) throws IOException {
        File file = new File(FILE_PATH);
        // Handles the case where the ./data directory does not exist.
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);
        for (Task task : tasks) {
            // Save the direct toString() output, e.g., "[T][X] read book"
            // This is easier to parse back than the numbered list format.
            fw.write(task.toFileString() + System.lineSeparator());
        }
        fw.close();
    }

    private static ArrayList<Task> loadFile() {
        File file = new File(FILE_PATH);
        ArrayList<Task> loadedTasks = new ArrayList<>();
        if (!file.exists()) {
            return loadedTasks;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                // --- FIX 1: IGNORE EMPTY LINES ---
                // If the line is blank, skip it and move to the next one.
                if (line.trim().isEmpty()) {
                    continue;
                }
                Task task = parseTaskFromFileString(line);
                // The parser might return null for corrupted lines, so we check for that.
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

    private static Task parseTaskFromFileString(String line) throws MonetException {
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