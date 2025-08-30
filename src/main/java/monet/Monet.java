package monet;

import java.io.IOException;

/**
 * Combines the different components: Ui, Storage, Parser, and TaskList.
 * Main class for the Monet chatbot application.
 */
public class Monet {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a new Monet application instance.
     * Initializes the UI, storage, and loads any existing tasks from the file.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Monet(String filePath){
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load()); // Load tasks from the storage file at startup.
        } catch (MonetException e) {
            ui.showError("Failed to load tasks: " + e.getMessage());
            tasks = new TaskList(); // Start with an empty task list if loading fails.
        }
    }

    /**
     * Runs the main application loop, processing user commands until the exit command is given.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.divider();
                Command command = Parser.parseCommand(fullCommand);

                // Switch case for different commands.
                switch (command) {
                case BYE:
                    isExit = true;
                    ui.showGoodbye();
                    break;
                case LIST:
                    ui.showTaskList(tasks);
                    break;
                case MARK:
                case UNMARK:
                    handleMarkUnmark(command, fullCommand);
                    break;
                case DELETE:
                    handleDelete(fullCommand);
                    break;
                case TODO:
                case DEADLINE:
                case EVENT:
                    handleAddTask(command, fullCommand);
                    break;
                default:
                    throw new MonetException("I don't know what that means. Please check your input!");
                }
            } catch (MonetException | IOException e) {
                // Catches both application-specific and file-related errors.
                ui.showError(e.getMessage());
            } finally {
                ui.divider();
            }
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
    private void handleAddTask(Command command, String fullCommand) throws MonetException, IOException {
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
            return; // Should not happen
        }
        tasks.addTask(newTask); // Execute the action: Add the task to the list.
        ui.showTaskAdded(newTask, tasks.getSize()); // Show UI confirmation.
        storage.save(tasks.getTasks()); // Save the updated list to disk.
    }

    /**
     * Parses the user input for a task index to delete, removes the task,
     * shows a confirmation, and saves the changes.
     *
     * @param fullCommand The full user input string (e.g., "delete 2").
     * @throws MonetException If the user input is in an invalid format.
     * @throws IOException If there is an error saving the tasks to the file.
     */
    private void handleDelete(String fullCommand) throws MonetException, IOException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        Task deletedTask = tasks.deleteTask(index);
        ui.showTaskDeleted(deletedTask, tasks.getSize());
        storage.save(tasks.getTasks());
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
    private void handleMarkUnmark(Command command, String fullCommand) throws MonetException, IOException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        Task task = tasks.getTask(index);
        if (command == Command.MARK) {
            task.markAsDone();
            ui.showTaskMarked(task);
        } else {
            task.unmarkAsDone();
            ui.showTaskUnmarked(task);
        }
        storage.save(tasks.getTasks());
    }

    /**
     * Creates an instance of Monet and starts the run loop.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Monet("./data/monet.txt").run();
    }
}
