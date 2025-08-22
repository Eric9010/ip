import java.util.ArrayList;
import java.util.Scanner;

public class Monet {
    public static void main(String[] args) {
        String chatbotName = "Monet";
        String divider = "____________________________________________________________________________________________";
        ArrayList<Task> tasks = new ArrayList<>();

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
                    for (int i = 0; i < tasks.size(); i++) {
                        // ArrayList uses .get(index) to access elements
                        System.out.println("  " + (i + 1) + "." + tasks.get(i));
                    }
                } else if (command.equals("mark") || command.equals("unmark") || command.equals("delete")) {
                    if (parts.length < 2) {
                        throw new MonetException("Please specify the task number to " + command + ".");
                    }
                    int taskIndex = Integer.parseInt(parts[1]) - 1;

                    // Use .size() for ArrayList boundary check
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new MonetException("Task number not found. Please provide a valid task number.");
                    }

                    if (command.equals("delete")) {
                        // ArrayList's .remove() method handles shifting elements automatically
                        Task removedTask = tasks.remove(taskIndex);
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removedTask);
                        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
                    } else if (command.equals("mark")) {
                        tasks.get(taskIndex).markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks.get(taskIndex));
                    } else { // unmark
                        tasks.get(taskIndex).unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks.get(taskIndex));
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

                    // Use .add() to add to the ArrayList
                    tasks.add(newTask);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newTask);
                    // Use .size() to get the current count
                    System.out.println(" Now you have " + tasks.size() + " tasks in the list.");

                } else {
                    throw new MonetException("I don't know what that means. Please check your input!");
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
