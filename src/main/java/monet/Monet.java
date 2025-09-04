package monet;

import java.io.IOException;

/**
 * Main class for the Monet chatbot application.
 * Supports a GUI interface using the UI class for displayed messages.
 */
public class Monet {

    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructor for Monet.
     * @param filePath The path to the file where tasks are stored.
     */
    public Monet(String filePath) {
        ui = new Ui(); // Instantiate the Ui class
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
    public String getWelcomeMessage() {
        return ui.getWelcomeMessage();
    }

    /**
     * Processes user input and returns the chatbot's response as a string using Ui class.
     * Main entry point for the GUI.
     *
     * @param input The user's input string.
     * @return The chatbot's response string.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parseCommand(input);
            switch (command) {
            case BYE:
                return ui.getGoodbyeMessage();
            case LIST:
                return ui.getTaskListMessage(tasks);
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
                return "I don't know what that means. Please check your input!";
            }
        } catch (MonetException | IOException e) {
            return ui.getErrorMessage(e.getMessage());
        }
    }

    /**
     * Parses the user input for a new task, adds it to the task list,
     * shows a confirmation to the user, and saves the updated list to the file.
     *
     * @param command The type of task to add (TODO, DEADLINE, or EVENT).
     * @param fullCommand The full user input string.
     * @throws MonetException If the user input is in an invalid format.
     * @throws IOException If there is an error saving the tasks to the file.
     */
    private String handleAddTask(Command command, String fullCommand) throws MonetException, IOException {
        Task newTask;

        // Parse the user input to create the correct task type.
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
            return ""; // Should not happen
        }

        tasks.addTask(newTask); // Execute the action: Add the task to the list.
        storage.save(tasks.getTasks()); // Save the updated list to disk.
        return ui.getTaskAddedMessage(newTask, tasks.getSize()); // Show UI confirmation.
    }

    /**
     * Parses the user input for a task index to delete, removes the task,
     * shows a confirmation, and saves the changes.
     *
     * @param fullCommand The full user input string (e.g., "delete 2").
     * @throws MonetException If the user input is in an invalid format.
     * @throws IOException If there is an error saving the tasks to the file.
     */
    private String handleDelete(String fullCommand) throws MonetException, IOException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        Task deletedTask = tasks.deleteTask(index);
        storage.save(tasks.getTasks());
        return ui.getTaskDeletedMessage(deletedTask, tasks.getSize());
    }

    /**
     * Parses the user input for a task index to mark or unmark, updates the task's status,
     * shows a confirmation, and saves the changes.
     *
     * @param command The action to perform (MARK or UNMARK).
     * @param fullCommand The full user input string (e.g., "mark 1").
     * @throws MonetException If the user input is in an invalid format.
     * @throws IOException If there is an error saving the tasks to the file.
     */
    private String handleMarkUnmark(Command command, String fullCommand) throws MonetException, IOException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        Task task = tasks.getTask(index);
        String response;
        if (command == Command.MARK) {
            task.markAsDone();
            response = ui.getTaskMarkedMessage(task);
        } else {
            task.unmarkAsDone();
            response = ui.getTaskUnmarkedMessage(task);
        }
        storage.save(tasks.getTasks());
        return response;
    }

    /**
     * Parses the user input for a keyword, finds matching tasks, and displays them.
     * This command does not modify the task list, so it does not save to the file.
     *
     * @param fullCommand The full user input string (e.g., "find book").
     * @throws MonetException If the keyword is missing from the input.
     */
    private String handleFind(String fullCommand) throws MonetException {
        String keyword = Parser.parseFind(fullCommand);
        TaskList foundTasks = tasks.findTasks(keyword);
        return ui.getFoundTasksMessage(foundTasks);
    }
}
