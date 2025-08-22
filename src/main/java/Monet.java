import java.util.Scanner;

public class Monet {
    public static void main(String[] args) {
        String chatbotName = "Monet";
        String divider = "____________________________________________________________________________________________";
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(" Hello! I'm " + chatbotName);
        System.out.println(" What can I do for you?");
        System.out.println(divider);

        Scanner in = new Scanner(System.in);

        while (true) {
            String input = in.nextLine();
            String[] parts = input.split(" ", 2); // Split command from arguments
            String command = parts[0];


            if (input.equals("bye")) {
                break; // Exit the loop if user types "bye"
            }else if (input.equals("list")){
                for (int i = 0; i < taskCount; i++) {
                    // List out stored tasks
                    System.out.println("  " + (i + 1) + ". " + tasks[i]);
                }
            }else if (command.equals("mark")) {
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
                // Add a new task
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("  added: " + input);
            }
        }
        // Farewell message
        System.out.println(" Bye. Hope to see you again soon!");

    }
}
