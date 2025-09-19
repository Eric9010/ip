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
                "What can i doth f'r thee?"
        );
    }

    /**
     * Returns the goodbye message for the chatbot.
     * @return A goodbye string.
     */
    public String getGoodbyeMessage() {
        return "Au revoir!";
    }

    /**
     * Returns a formatted error message.
     * @param message The error message content.
     * @return A formatted error string.
     */
    public String getErrorMessage(String message) {
        return "Alas! " + message;
    }

    /**
     * Returns a formatted string representing the list of tasks.
     * @param tasks The TaskList to be displayed.
     * @return A formatted string of the tasks.
     */
    public String getTaskListMessage(TaskList tasks) {
        if (tasks.getSize() == 0) {
            return "Thy task listeth is barren. Addeth some tasks!";
        }
        return formatTaskList("H're art the tasks in thy listeth:", tasks);
    }

    /**
     * Returns a formatted string for a newly added task.
     * @param task The task that was added.
     * @param taskCount The new total number of tasks.
     * @return A formatted confirmation string.
     */
    public String getTaskAddedMessage(Task task, int taskCount) {
        return formatMessages(
                "Ay. I has't did add this task:",
                "   " + task,
                "Anon thee has't " + taskCount + " tasks in thy listeth."
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
                "Ay. I've did remove this task:",
                "   " + task,
                "Anon thee has't " + taskCount + " tasks in thy listeth."
        );
    }

    /**
     * Returns a formatted string for a task marked as done.
     * @param task The task that was marked.
     * @return A formatted confirmation string.
     */
    public String getTaskMarkedMessage(Task task) {
        return formatMessages(
                "That's good that's gone! I've did mark this task as done:",
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
                "'Tis agreed, I've unmark'd this task:",
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
            return "Nay matching tasks w're hath found.";
        }
        return formatTaskList("H're art the matching tasks in thy listeth:", tasks);
    }


    /**
     * Formats a list of tasks filtered by priority into a display-ready string.
     *
     * @param priority The priority level that was used for filtering, included in the message header.
     * @param tasks The TaskList (presumably pre-filtered) containing the tasks to be displayed.
     * @return A formatted string containing the list of tasks, or a 'not found' message.
     */
    public String showPriorityTaskList(Priority priority, TaskList tasks) {
        if (tasks.getSize() == 0) {
            return "Nay tasks hath found with " + priority.name() + " priority.";
        }
        // Formats and returns the full list of tasks.
        return formatTaskList("Showing tasks with " + priority.name() + " priority:", tasks);
    }

    // Helper method to format the list of tasks
    private String formatTaskList(String header, TaskList tasks) {
        StringBuilder sb = new StringBuilder(header);
        sb.append("\n");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append("  ").append(i + 1).append(".").append(tasks.getTask(i).toString());
            // Append a newline for each task except the last one
            if (i < tasks.getSize() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
