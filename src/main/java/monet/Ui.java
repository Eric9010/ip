package monet;

import java.util.Scanner;

/**
 * Handles the formatting of messages to be displayed to the user.
 * Now decoupled from the console and instead returns strings for GUI use.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a new Ui task.
     * Initialises scanner to allow user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the welcome message for the chatbot.
     * @return A welcome string.
     */
    public String getWelcomeMessage() {
        return "Hello! I'm Monet\nWhat can I do for you?";
    }

    /**
     * Returns the goodbye message for the chatbot.
     * @return A goodbye string.
     */
    public String getGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a formatted error message.
     * @param message The error message content.
     * @return A formatted error string.
     */
    public String getErrorMessage(String message) {
        return "Sorry! " + message;
    }

    /**
     * Returns a formatted string representing the list of tasks.
     * @param tasks The TaskList to be displayed.
     * @return A formatted string of the tasks.
     */
    public String getTaskListMessage(TaskList tasks) {
        if (tasks.getSize() == 0) {
            return "Your task list is empty. Add some tasks!";
        }
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            // Append a newline character for each task except the last one.
            sb.append("  ").append(i + 1).append(".").append(tasks.getTask(i).toString());
            if (i < tasks.getSize() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Returns a formatted string for a newly added task.
     * @param task The task that was added.
     * @param taskCount The new total number of tasks.
     * @return A formatted confirmation string.
     */
    public String getTaskAddedMessage(Task task, int taskCount) {
        return String.format("Got it. I've added this task:\n   %s\nNow you have %d tasks in the list.",
                task, taskCount);
    }

    /**
     * Returns a formatted string for a deleted task.
     * @param task The task that was deleted.
     * @param taskCount The new total number of tasks.
     * @return A formatted confirmation string.
     */
    public String getTaskDeletedMessage(Task task, int taskCount) {
        return String.format("Noted. I've removed this task:\n   %s\nNow you have %d tasks in the list.",
                task, taskCount);
    }

    /**
     * Returns a formatted string for a task marked as done.
     * @param task The task that was marked.
     * @return A formatted confirmation string.
     */
    public String getTaskMarkedMessage(Task task) {
        return "Nice! I've marked this task as done:\n   " + task;
    }

    /**
     * Returns a formatted string for a task marked as not done.
     * @param task The task that was unmarked.
     * @return A formatted confirmation string.
     */
    public String getTaskUnmarkedMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n   " + task;
    }

    /**
     * Returns a formatted string for the results of a find command.
     * @param tasks The TaskList of found tasks.
     * @return A formatted string of the search results.
     */
    public String getFoundTasksMessage(TaskList tasks) {
        if (tasks.getSize() == 0) {
            return "No tasks matching your keyword were found.";
        }
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append("  ").append(i + 1).append(".").append(tasks.getTask(i).toString());
            if (i < tasks.getSize() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
