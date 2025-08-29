package monet;

public class Parser {

    // Parses the user's full input string to determine the command type based on monet.Command enum.
    public static Command parseCommand(String fullInput) {
        String commandWord = fullInput.split(" ")[0].toLowerCase();

        switch (commandWord) {
            case "list":
                return Command.LIST;
            case "mark":
                return Command.MARK;
            case "unmark":
                return Command.UNMARK;
            case "delete":
                return Command.DELETE;
            case "todo":
                return Command.TODO;
            case "deadline":
                return Command.DEADLINE;
            case "event":
                return Command.EVENT;
            case "bye":
                return Command.BYE;
            default:
                return Command.UNKNOWN;
        }
    }

    // Parses the arguments for a "todo" command
    public static String parseTodo(String fullInput) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("The description for a todo cannot be empty.");
        }
        return parts[1];
    }

    // Parses the arguments for a "deadline" command.
    public static String[] parseDeadline(String fullInput) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("The description for a deadline cannot be empty.");
        }

        String[] deadlineParts = parts[1].split(" /by ", 2);
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            throw new MonetException("Invalid deadline format. Use: deadline <description> /by <date>");
        }
        return deadlineParts;
    }

    // Parses the arguments for an "event" command.
    public static String[] parseEvent(String fullInput) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("The description for an event cannot be empty.");
        }

        String[] eventParts = parts[1].split(" /from ", 2);
        if (eventParts.length < 2 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty()) {
            throw new MonetException("Invalid event format. Use: event <description> /from <start> /to <end>");
        }

        String[] timeParts = eventParts[1].split(" /to ", 2);
        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new MonetException("Invalid event format. Use: event <description> /from <start> /to <end>");
        }

        return new String[]{eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim()};
    }

    // Parses the task index from commands like "mark", "unmark", and "delete"
    public static int parseIndex(String fullInput, int listSize) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("Please specify the task number.");
        }
        try {
            int index = Integer.parseInt(parts[1].trim()) - 1; // Convert to 0-based index
            if (index < 0 || index >= listSize) {
                throw new MonetException("monet.Task number not found. Please provide a valid task number.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new MonetException("Please enter a valid number for the task index.");
        }
    }
}
