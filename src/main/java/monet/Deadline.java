package monet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline. It is a subclass of Task.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    // Defines the expected format for user date/time input.
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    // Defines the desired format for displaying the date/time to the user.
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructs a Deadline task from user input.
     * Parses the date string into a LocalDateTime object.
     *
     * @param description The description of the deadline task.
     * @param byString The date/time string for the deadline.
     * @param priority The priority of the deadline task.
     * @throws MonetException If the date/time string is in an invalid format.
     */
    public Deadline(String description, String byString, Priority priority) throws MonetException {
        super(description, priority);
        try {
            // Attempt to parse the user-provided date string.
            this.by = LocalDateTime.parse(byString.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            // If parsing fails, throw a custom exception with a relevant message.
            throw new MonetException("Invalid date format for deadline. Please use 'yyyy-MM-dd HHmm'.");
        }
    }

    /**
     * Constructs a Deadline task when loading from the data file.
     *
     * @param description The description of the deadline task.
     * @param by A pre-parsed LocalDateTime object.
     * @param priority The priority of the deadline task.
     */
    public Deadline(String description, LocalDateTime by, Priority priority) {
        super(description, priority);
        this.by = by;
    }

    @Override
    public String toString() {
        // Formats the LocalDateTime object into a user-friendly string for display.
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMATTER) + ")";
    }

    @Override
    public String toFileString() {
        // Converts the task to a machine-readable string for saving to file.
        return "D | " + (isDone ? "1" : "0") + " | " + priority.name() + " | " + description + " | " + by;
    }
}
