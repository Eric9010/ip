import java.io.IOException;

public class Monet {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    public Monet(String filePath){
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MonetException e) {
            ui.showError("Failed to load tasks: " + e.getMessage());
            tasks = new TaskList(); // Start with an empty task list if loading fails
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.divider();
                Command command = Parser.parseCommand(fullCommand);

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
                ui.showError(e.getMessage());
            } finally {
                ui.divider();
            }
        }
    }

    private void handleAddTask(Command command, String fullCommand) throws MonetException, IOException {
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
                return; // Should not happen
        }
        tasks.addTask(newTask);
        ui.showTaskAdded(newTask, tasks.getSize());
        storage.save(tasks.getTasks());
    }

    private void handleDelete(String fullCommand) throws MonetException, IOException {
        int index = Parser.parseIndex(fullCommand, tasks.getSize());
        Task deletedTask = tasks.deleteTask(index);
        ui.showTaskDeleted(deletedTask, tasks.getSize());
        storage.save(tasks.getTasks());
    }



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

    public static void main(String[] args) {
        new Monet("./data/monet.txt").run();
    }
}
