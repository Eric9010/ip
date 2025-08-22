import java.util.Scanner;

public class Monet {
    public static void main(String[] args) {
        String chatbotName = "Monet";
        String divider = "--------------------------------------------------------------------------------------------";

        System.out.println(" Hello! I'm " + chatbotName);
        System.out.println(" What can I do for you?");
        System.out.println(divider);

        Scanner in = new Scanner(System.in);

        // Start of command loop that echoes inputs
        while (true) {
            String input = in.nextLine();
            if (input.equals("bye")) {
                break; // Exit the loop if user types "bye"
            }
            // Echo the user's command
            System.out.println("  " + input);
        }

        // Farewell message
        System.out.println(" Bye. Hope to see you again soon!");

    }
}
