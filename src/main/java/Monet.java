import java.util.Scanner;

public class Monet {
    public static void main(String[] args) throws MonetException {
        String chatbotName = "Monet";
        String divider = "____________________________________________________________________________________________";
        Task[] tasks = new Task[100]; // Task array that holds any type of task
        int taskCount = 0;

        System.out.println(" Hello! I'm " + chatbotName);
        System.out.println(" What can I do for you?");
        System.out.println(divider);

        Scanner in = new Scanner(System.in);

        while (true) {
            try {
                String input = in.nextLine();
                String[] parts = input.split(" ", 2);
                String command = parts[0];

                System.out.println(divider);

                if (command.equals("bye")) {
                    break;
                } else if (command.equals("list")) {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("  " + (i + 1) + "." + tasks[i]);
                    }
                } else if (command.equals("mark") || command.equals("unmark")) {
                    if (parts.length < 2) {
                        throw new MonetException("Please specify the task number to mark/unmark.");
                    }
                    int taskIndex = Integer.parseInt(parts[1]) - 1;
                    if (taskIndex < 0 || taskIndex >= taskCount) {
                        throw new MonetException("Task number not found. Please provide a valid task number.");
                    }

                    if (command.equals("mark")) {
                        tasks[taskIndex].markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[taskIndex]);
                    } else {
                        tasks[taskIndex].unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[taskIndex]);
                    }
                } else if (command.equals("todo") || command.equals("deadline") || command.equals("event")) {
                    Task newTask = null;
                    if (command.equals("todo")) {
                        if (parts.length < 2) {
                            throw new MonetException("The description for a todo cannot be empty.");
                        }
                        newTask = new Todo(parts[1]);
                    } else if (command.equals("deadline")) {
                        if (parts.length < 2 || !parts[1].contains(" /by ")) {
                            throw new MonetException("Invalid deadline format. Use: deadline <desc> /by <date>");
                        }
                        String[] deadlineParts = parts[1].split(" /by ");
                        newTask = new Deadline(deadlineParts[0].trim(), deadlineParts[1].trim());
                    } else if (command.equals("event")) {
                        if (parts.length < 2 || !parts[1].contains(" /from ") || !parts[1].contains(" /to ")) {
                            throw new MonetException("Invalid event format. Use: event <desc> /from <date> /to <date>");
                        }
                        String[] eventParts = parts[1].split(" /from ");
                        String[] timeParts = eventParts[1].split(" /to ");
                        newTask = new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
                    }

                    tasks[taskCount] = newTask;
                    taskCount++;
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newTask);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");

                } else {
                    throw new MonetException("I don't know what that means.");
                }
            } catch (MonetException e) {
                System.out.println("Sorry! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Sorry! Please enter a valid number for the task index.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            } finally {
                System.out.println(divider);
            }
        }
        // Farewell message
        System.out.println(" Bye. Hope to see you again soon!");
    }
}
