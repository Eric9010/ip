package monet;

import javafx.util.Pair;

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
        case "find":
            return Command.FIND;
        case "priority": // NEW: Handle the priority command
            return Command.PRIORITY;
        default:
            return Command.UNKNOWN;
        }
    }

    private static Pair<String, Priority> extractPriority(String content) throws MonetException {
        // Splits the content by the "/p" flag.
        String[] parts = content.split(" /p ");
        if (parts.length > 1) {
            // Priority flag exists
            String remainingContent = parts[0].trim();
            try {
                int level = Integer.parseInt(parts[1].trim());
                return new Pair<>(remainingContent, Priority.of(level));
            } catch (NumberFormatException e) {
                throw new MonetException("Priority level must be a number (1, 2, or 3).");
            }
        } else {
            // No priority flag, return the original content and default priority
            return new Pair<>(content, Priority.MEDIUM);
        }
    }

    /**
     * Parses the arguments for a "todo" command.
     * Expected format: "to do [description] /p [level]"
     *
     * @param fullInput The full user input string.
     * @return The description of the todo task.
     * @throws MonetException If the description is empty.
     */
    public static Object[] parseTodo(String fullInput) throws MonetException {
        // Splits the input into 2 parts: the command word and the rest of the string.
        // The limit '2' ensures the description, which may contain spaces, is kept as one part.
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("The description for a todo cannot be empty.");
        }

        String content = parts[1];
        Pair<String, Priority> result = extractPriority(content);
        return new Object[]{result.getKey(), result.getValue()};
    }

    /**
     * Parses the arguments for a "deadline" command.
     * Expected format: "deadline [description] /by [date] /p [level]"
     *
     * @param fullInput The full user input string.
     * @return A String array containing the description [0] and the deadline string [1].
     * @throws MonetException If the format is incorrect or parts are missing.
     */
    public static Object[] parseDeadline(String fullInput) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("The description f'r a deadline cannot beest barren.");
        }

        Pair<String, Priority> contentAndPriority = extractPriority(parts[1]);
        String content = contentAndPriority.getKey();
        Priority priority = contentAndPriority.getValue();

        // The description part is then split by the "/by" delimiter to separate content and date.
        String[] deadlineParts = content.split(" /by ", 2);
        if (deadlineParts.length < 2 || deadlineParts[0].trim().isEmpty() || deadlineParts[1].trim().isEmpty()) {
            throw new MonetException("Invalid format. Prithee useth: deadline <description> /by <date>");
        }
        return new Object[]{deadlineParts[0].trim(), deadlineParts[1].trim(), priority};
    }

    /**
     * Parses the arguments for an "event" command.
     * Expected format: "event [description] /from [yyyy-MM-dd HHmm] /to [yyyy-MM-dd HHmm]"
     *
     * @param fullInput The full user input string.
     * @return A String array containing the description [0], from-time [1], and to-time [2].
     * @throws MonetException If the format is incorrect or parts are missing.
     */
    public static Object[] parseEvent(String fullInput) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("The description f'r an event cannot beest barren.");
        }

        Pair<String, Priority> contentAndPriority = extractPriority(parts[1]);
        String content = contentAndPriority.getKey();
        Priority priority = contentAndPriority.getValue();

        String[] eventParts = content.split(" /from ", 2);
        if (eventParts.length < 2 || eventParts[0].trim().isEmpty() || eventParts[1].trim().isEmpty()) {
            throw new MonetException("Invalid format. Prithee useth: event <description> /from <start> /to <end>");
        }

        String[] timeParts = eventParts[1].split(" /to ", 2);
        if (timeParts.length < 2 || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            throw new MonetException("Invalid format. Prithee useth: event <description> /from <start> /to <end>");
        }

        return new Object[]{eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim(), priority};
    }

    /**
     * Parses the keyword for a "find" command.
     * Expected format: "find [keyword]"
     *
     * @param fullInput The full user input string.
     * @return The keyword to search for.
     * @throws MonetException If the keyword is missing.
     */
    public static String parseFind(String fullInput) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new MonetException("Prithee ent'r a keyword to findeth.");
        }
        return parts[1].trim();
    }

    // Parses the task index from commands like "mark", "unmark", and "delete"
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
            throw new MonetException("Prithee specifyeth the task number.");
        }
        try {
            // Convert the user-provided 1-based index to a 0-based index for the ArrayList.
            int index = Integer.parseInt(parts[1].trim()) - 1;

            // Validate that the index is within the bounds of the current task list size.
            if (index < 0 || index >= listSize) {
                throw new MonetException("Task number not hath found.  Prithee provideth a valid task number");
            }
            return index;
        } catch (NumberFormatException e) {
            // Catch cases where the user types e.g., "mark one" instead of "mark 1".
            throw new MonetException("Prithee ent'r a valid number f'r the task index");
        }
    }

    /**
     * Parses the priority level from a "priority" command.
     * @param fullInput The full user input (e.g., "priority 1").
     * @return The Priority enum.
     * @throws MonetException If the level is missing or not a number.
     */
    public static Priority parsePriorityLevel(String fullInput) throws MonetException {
        String[] parts = fullInput.split(" ", 2);
        if (parts.length < 2) {
            throw new MonetException("Prithee specifyeth a priority leveleth (1=High, 2=Medium, 3=Low).");
        }
        try {
            int level = Integer.parseInt(parts[1].trim());
            return Priority.of(level);
        } catch (NumberFormatException e) {
            throw new MonetException("Priority leveleth might not but beest a number (1, 2, or 3).");
        }
    }
}
