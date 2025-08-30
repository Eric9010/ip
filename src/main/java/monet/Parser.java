package monet;

/**
 * Deals with making sense of the user command.
 * Contains methods to parse various types of commands and their arguments.
 */
public class Parser {
    /**
     * Parses the user's full input string to determine the command type.
     *
     * @param fullInput The full line of input from the user.
     * @return The corresponding Command enum.
     */
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

    /**
     * Parses the arguments for a "todo" command.
     * Expected format: "todo <description>"
     *
     * @param fullInput The full user input string.
     * @return The description of the todo task.
     * @throws MonetException If the description is empty.
     */
    public static String parseTodo(String fullInput) throws MonetException {
        // Splits the input into 2 parts: the command word and the rest of the string.
        // The limit '2' ensures the description, which may contain spaces, is kept as one part.
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("The description for a todo cannot be empty.");
        }
        return parts[1];
    }

    /**
     * Parses the arguments for a "deadline" command.
     * Expected format: "deadline <description> /by <yyyy-MM-dd HHmm>"
     *
     * @param fullInput The full user input string.
     * @return A String array containing the description [0] and the deadline string [1].
     * @throws MonetException If the format is incorrect or parts are missing.
     */
    public static String[] parseDeadline(String fullInput) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("The description for a deadline cannot be empty.");
        }

        // The description part is then split by the "/by" delimiter to separate content and date.
        String[] deadlineParts = parts[1].split(" /by ", 2);
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            throw new MonetException("Invalid deadline format. Use: deadline <description> /by <date>");
        }
        return deadlineParts;
    }

    /**
     * Parses the arguments for an "event" command.
     * Expected format: "event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>"
     *
     * @param fullInput The full user input string.
     * @return A String array containing the description [0], from-time [1], and to-time [2].
     * @throws MonetException If the format is incorrect or parts are missing.
     */
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

        // Return the three distinct parts: description, from-time, and to-time.
        return new String[]{eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim()};
    }

    /**
     * Parses the task index from commands like "mark", "unmark", and "delete".
     *
     * @param fullInput The full user input string (e.g., "mark 2").
     * @param listSize The current size of the task list for validation.
     * @return The 0-based index of the task.
     * @throws MonetException If the index is not a number or is out of bounds.
     */
    public static int parseIndex(String fullInput, int listSize) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("Please specify the task number.");
        }
        try {
            // Convert the user-provided 1-based index to a 0-based index for the ArrayList.
            int index = Integer.parseInt(parts[1].trim()) - 1;

            // Validate that the index is within the bounds of the current task list size.
            if (index < 0 || index >= listSize) {
                throw new MonetException("monet.Task number not found. Please provide a valid task number.");
            }
            return index;
        } catch (NumberFormatException e) {
            // Catch cases where the user types e.g., "mark one" instead of "mark 1".
            throw new MonetException("Please enter a valid number for the task index.");
        }
    }
}
