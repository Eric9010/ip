import java.util.Scanner;

public class Monet {
    public static void main(String[] args) {
        String chatbotName = "Monet";
        String divider = "____________________________________________________________________________________________";

        System.out.println(" Hello! I'm " + chatbotName);
        System.out.println(" What can I do for you?");
        System.out.println(divider);
        String[] tasks = new String[100]; // Array to store up to 100 tasks
        int taskCount = 0; // To keep track of the number of tasks

        Scanner in = new Scanner(System.in);

        while (true) {
            String input = in.nextLine();
            if (input.equals("bye")) {
                break; // Exit the loop if user types "bye"
            }else if (input.equals("list")){
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("  " + (i + 1) + ". " + tasks[i]); // List out stored tasks
                }
            }else{
                tasks[taskCount] = input; // Add inputted task to list
                taskCount++;
                System.out.println("  added: " + input);
            }
        }
        // Farewell message
        System.out.println(" Bye. Hope to see you again soon!");

    }
}
