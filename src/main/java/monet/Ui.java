package monet;

import java.util.Scanner;

/**
 * Handles all user interface interactions. This includes printing messages to the console
 * and reading user input.
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
     * Reads the next full line of input from the user.
     * @return The user's input string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the welcome message when the chatbot starts.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm monet.Monet");
        System.out.println("What can I do for you?");
        divider();
    }

    /**
     * Prints the goodbye message when the chatbot exits.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints an error message.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println("Sorry! " + message);
    }

    /**
     * Prints a divider for visual separation.
     */
    public void divider() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Prints the list of tasks to the user.
     *
     * @param tasks The TaskList containing the tasks to be displayed.
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.getSize() == 0) {
            System.out.println("Your task list is empty. Add some tasks!");
            return;
        }
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println("  " + (i + 1) + "." + tasks.getTask(i));
        }
    }

    /**
     * Prints a confirmation message after a task has been successfully added.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks in the list now.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints a confirmation message after a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param taskCount The total number of tasks remaining in the list.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints a confirmation message after a task has been marked as done.
     *
     * @param task The task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /**
     * Prints a confirmation message after a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }

    /**
     * Prints the list of tasks found by a search.
     * Displays a special message if no tasks are found.
     *
     * @param tasks The TaskList containing the tasks to be displayed.
     */
    public void showFoundTasks(TaskList tasks) {
        if (tasks.getSize() == 0) {
            System.out.println(" No tasks matching your keyword were found.");
            return;
        }
        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.getSize(); i++) {
            System.out.println("  " + (i + 1) + "." + tasks.getTask(i));
        }
    }
}
