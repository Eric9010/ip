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
     * Formats multiple lines of text into a single, newline-separated string.
     * Utility method that makes use of varargs.
     *
     * @param messages A variable number of strings to be joined.
     * @return A single formatted string.
     */
    private String formatMessages(String... messages) {
        // Inside the method, 'messages' is treated as a String array (String[]).
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messages.length; i++) {
            sb.append(messages[i]);
            // Append a newline after each message except the last one.
            if (i < messages.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Returns the welcome message for the chatbot.
     * @return A welcome string.
     */
    public String getWelcomeMessage() {
        // Refactored: Uses the varargs helper for cleaner code.
        return formatMessages(
                "Hello! I'm Monet",
                "What can I do for you?"
        );
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
        return formatMessages(
                "Got it. I've added this task:",
                "   " + task,
                "Now you have " + taskCount + " tasks in the list."
        );
    }

    /**
     * Returns a formatted string for a deleted task.
     * @param task The task that was deleted.
     * @param taskCount The new total number of tasks.
     * @return A formatted confirmation string.
     */
    public String getTaskDeletedMessage(Task task, int taskCount) {
        return formatMessages(
                "Noted. I've removed this task:",
                "   " + task,
                "Now you have " + taskCount + " tasks in the list."
        );
    }

    /**
     * Returns a formatted string for a task marked as done.
     * @param task The task that was marked.
     * @return A formatted confirmation string.
     */
    public String getTaskMarkedMessage(Task task) {
        return formatMessages(
                "Nice! I've marked this task as done:",
                "   " + task
        );
    }

    /**
     * Returns a formatted string for a task marked as not done.
     * @param task The task that was unmarked.
     * @return A formatted confirmation string.
     */
    public String getTaskUnmarkedMessage(Task task) {
        return formatMessages(
                "OK, I've marked this task as not done yet:",
                "   " + task
        );
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
