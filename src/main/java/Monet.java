import java.io.IOException;
import java.util.Scanner;

public class Monet {
    private static TaskList tasks;
    private static Storage storage;

    public static void main(String[] args) {
        String chatbotName = "Monet";
        String divider = "____________________________________________________________";

        storage = new Storage("./data/monet.txt");
        tasks = new TaskList(storage.load());

        System.out.println(" Hello! I'm " + chatbotName);
        System.out.println(" What can I do for you?");
        System.out.println(divider);

        Scanner in = new Scanner(System.in);

        while (true) {
            try {
                String input = in.nextLine();
                Command command = Parser.parseCommand(input);

                if (command == Command.BYE) {
                    break;
                }

                switch (command) {
                case LIST:
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < tasks.getSize(); i++) {
                        System.out.println("  " + (i + 1) + "." + tasks.getTask(i));
                    }
                    break;

                case MARK:
                case UNMARK: {
                    int taskIndex = Parser.parseIndex(input, tasks.getSize());
                    Task task = tasks.getTask(taskIndex);
                    if (command == Command.MARK) {
                        task.markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                    } else {
                        task.unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                    }
                    System.out.println("   " + task);
                    storage.save(tasks.getTasks()); // Save changes to the file
                    break;
                }

                case DELETE: {
                    int taskIndex = Parser.parseIndex(input, tasks.getSize());
                    Task removedTask = tasks.deleteTask(taskIndex);
                    System.out.println(" Noted. I've removed this task:");
                    System.out.println("   " + removedTask);
                    System.out.println(" Now you have " + tasks.getSize() + " tasks in the list.");
                    storage.save(tasks.getTasks()); // Save changes to the file
                    break;
                }

                case TODO: {
                    String description = Parser.parseTodo(input);
                    Todo newTodo = new Todo(description);
                    tasks.addTask(newTodo);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newTodo);
                    System.out.println(" Now you have " + tasks.getSize() + " tasks in the list.");
                    storage.save(tasks.getTasks()); // Save changes to the file
                    break;
                }

                case DEADLINE: {
                    String[] deadlineDetails = Parser.parseDeadline(input);
                    Deadline newDeadline = new Deadline(deadlineDetails[0], deadlineDetails[1]);
                    tasks.addTask(newDeadline);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newDeadline);
                    System.out.println(" Now you have " + tasks.getSize() + " tasks in the list.");
                    storage.save(tasks.getTasks()); // Save changes to the file
                    break;
                }

                case EVENT: {
                    String[] eventDetails = Parser.parseEvent(input);
                    Event newEvent = new Event(eventDetails[0], eventDetails[1], eventDetails[2]);
                    tasks.addTask(newEvent);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + newEvent);
                    System.out.println(" Now you have " + tasks.getSize() + " tasks in the list.");
                    storage.save(tasks.getTasks()); // Save changes to the file
                    break;
                }

                case UNKNOWN:
                default:
                    throw new MonetException("I don't know what that means. Please check your input!");
                }

            } catch (MonetException | IOException e) {
                System.out.println("Sorry! " + e.getMessage());
            } finally {
                System.out.println(divider);
            }
        }
        System.out.println(" Bye. Hope to see you again soon!");
    }
}