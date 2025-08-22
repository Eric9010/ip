import java.util.Scanner;

public class Monet {
    public static void main(String[] args) {
        String chatbotName = "Monet";
        String divider = "____________________________________________________________________________________________";
        Task[] tasks = new Task[100]; // Task array that holds any type of task
        int taskCount = 0;

        System.out.println(" Hello! I'm " + chatbotName);
        System.out.println(" What can I do for you?");
        System.out.println(divider);

        Scanner in = new Scanner(System.in);

        while (true) {
            String input = in.nextLine();
            // Split the input into a command word and the rest of the string (arguments)
            // The '2' limits the split to two parts, useful for descriptions containing spaces
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            if (input.equals("bye")) {
                break; // Exit the loop if user types "bye"
            }else if (input.equals("list")){
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    // List out stored tasks using the correct .toString() specified in Task
                    System.out.println("  " + (i + 1) + ". " + tasks[i]);
                }
            }else if (command.equals("mark")) {
                // Parse the task number from the arguments, subtracting 1 for the 0-based array index
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[taskIndex]);
            } else if (command.equals("unmark")) {
                int taskIndex = Integer.parseInt(parts[1]) - 1;
                tasks[taskIndex].unmarkAsDone();
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[taskIndex]);
            } else {
                // Creating new task types
                Task newTask = null;
                if (command.equals("todo")) {
                    newTask = new Todo(parts[1]); // Create To do object
                } else if (command.equals("deadline")) {
                    // For deadlines, we split the arguments by " /by " to separate the description and the date
                    String[] deadlineParts = parts[1].split(" /by ");
                    newTask = new Deadline(deadlineParts[0], deadlineParts[1]);
                } else if (command.equals("event")) {
                    // For events, we do a nested split for "/from" and "/to"
                    String[] eventParts = parts[1].split(" /from ");
                    String[] timeParts = eventParts[1].split(" /to ");
                    newTask = new Event(eventParts[0], timeParts[0], timeParts[1]);
                }

                // If a new task was successfully created, add it to the array
                if (newTask != null) {
                    tasks[taskCount] = newTask;
                    taskCount++;
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newTask);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                }
            }
            System.out.println(divider);
        }
        // Farewell message
        System.out.println(" Bye. Hope to see you again soon!");
    }
}
