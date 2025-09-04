package monet;

import java.io.IOException;

/**
 * The main class for the Monet chatbot application.
 * This class now supports both a command-line interface and a GUI interface.
 */
public class Monet {

    private final Storage storage;
    private TaskList tasks;

    /**
     * Constructs a new Monet application instance.
     * Initializes the UI, storage, and loads any existing tasks from the file.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Monet(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MonetException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Returns the welcome message for the chatbot.
     * @return A welcome string.
     */
    public static String getWelcomeMessage() {
        return "Hello! I'm Monet\nWhat can I do for you?";
    }

    /**
     * Processes user input and returns the chatbot's response as a string.
     * This is the main entry point for the GUI.
     *
     * @param input The user's input string.
     * @return The chatbot's response string.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parseCommand(input);
            switch (command) {
            case BYE:
                return "Bye. Hope to see you again soon!";
            case LIST:
                return handleList();
            case MARK:
            case UNMARK:
                return handleMarkUnmark(command, input);
            case DELETE:
                return handleDelete(input);
            case TODO:
            case DEADLINE:
            case EVENT:
                return handleAddTask(command, input);
            case FIND:
                return handleFind(input);
            default:
                return "I'm sorry, I don't know what that means :-(";
            }
        } catch (MonetException | IOException e) {
            return "Whoops! " + e.getMessage();
        }
    }

    private String handleList() {
        if (tasks.getSize() == 0) {
            return "Your task list is empty. Add some tasks!";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append("  ").append(i + 1).append(".").append(tasks.getTask(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }

    private String handleAddTask(Command command, String fullCommand) throws MonetException, IOException {
        Task newTask;
        switch (command) {
        case TODO:
            newTask = new Todo(Parser.parseTodo(fullCommand));
            break;
        case DEADLINE:
            String[] deadlineDetails = Parser.parseDeadline(fullCommand);
            newTask = new Deadline(deadlineDetails[0], deadlineDetails[1]);
            break;
        case EVENT:
            String[] eventDetails = Parser.parseEvent(fullCommand);
            newTask = new Event(eventDetails[0], eventDetails[1], eventDetails[2]);
            break;
        default:
            return "";
        }
        tasks.addTask(newTask);
        storage.save(tasks.getTasks());
        return String.format("Got it. I've added this task:\n   %s\nNow you have %d tasks in the list.",
                newTask, tasks.getSize());
    }

    private String handleDelete(String fullCommand) throws MonetException, IOException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        Task deletedTask = tasks.deleteTask(index);
        storage.save(tasks.getTasks());
        return String.format("Noted. I've removed this task:\n   %s\nNow you have %d tasks in the list.",
                deletedTask, tasks.getSize());
    }

    private String handleMarkUnmark(Command command, String fullCommand) throws MonetException, IOException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        Task task = tasks.getTask(index);
        String response;
        if (command == Command.MARK) {
            task.markAsDone();
            response = "Nice! I've marked this task as done:\n   " + task;
        } else {
            task.unmarkAsDone();
            response = "OK, I've marked this task as not done yet:\n   " + task;
        }
        storage.save(tasks.getTasks());
        return response;
    }

    private String handleFind(String fullCommand) throws MonetException {
        String keyword = Parser.parseFind(fullCommand);
        TaskList foundTasks = tasks.findTasks(keyword);
        if (foundTasks.getSize() == 0) {
            return "No tasks matching your keyword were found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < foundTasks.getSize(); i++) {
            sb.append("  ").append(i + 1).append(".").append(foundTasks.getTask(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }
}
