import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Monet {
    /** Static fields for file handing */
    private static final String FILE_PATH = "./data/monet.txt";
    private static ArrayList<Task> tasks;

    public static void main(String[] args) {
        String chatbotName = "Monet";
        String divider = "____________________________________________________________";

        tasks = loadFile(); // Load tasks from the file at startup.

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
            fw.write(task.toString() + System.lineSeparator());
        }
        fw.close();
    }

    private static ArrayList<Task> loadFile() {
        File file = new File(FILE_PATH);
        ArrayList<Task> loadedTasks = new ArrayList<>();

        // Handle case where file does not exist gracefully.
        if (!file.exists()) {
            return loadedTasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                Task task = parseTaskFromFileString(line);
                if (task != null) {
                    loadedTasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            // This case is handled by the file.exists() check, but it's good practice.
            System.out.println("File not found, will be created on first save.");
        } catch (MonetException e) {
            System.out.println("Error parsing file: " + e.getMessage() + ". Starting with an empty task list.");
            return new ArrayList<>(); // Return empty list if file is corrupt.
        }
        return loadedTasks;
    }

    private static Task parseTaskFromFileString(String line) throws MonetException {
        // Example formats:
        // [T][ ] read book
        // [D][X] return book (by: Sunday)
        // [E][ ] project meeting (from: Mon 2pm to: 4pm)

        char type = line.charAt(1);
        boolean isDone = line.charAt(4) == 'X';
        Task task;

        String content = line.substring(7); // Get the main content part of the task string

        switch (type) {
            case 'T':
                task = new Todo(content);
                break;
            case 'D':
                // Split "return book (by: Sunday)" into "return book" and "Sunday)"
                String[] deadlineParts = content.split(" \\(by: ");
                String deadlineDesc = deadlineParts[0];
                // Remove the closing parenthesis
                String by = deadlineParts[1].substring(0, deadlineParts[1].length() - 1);
                task = new Deadline(deadlineDesc, by);
                break;
            case 'E':
                // Split "project meeting (from: Mon 2pm to: 4pm)" into "project meeting" and "Mon 2pm to: 4pm)"
                String[] eventParts = content.split(" \\(from: ");
                String eventDesc = eventParts[0];
                // Split "Mon 2pm to: 4pm)" into "Mon 2pm" and "4pm"
                String[] timeParts = eventParts[1].split(" to: ");
                String from = timeParts[0];
                // Remove the closing parenthesis
                String to = timeParts[1].substring(0, timeParts[1].length() - 1);
                task = new Event(eventDesc, from, to);
                break;
            default:
                throw new MonetException("Unknown task type in file: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}