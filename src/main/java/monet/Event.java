package monet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that is an event with a start and end time. It is a subclass of Task.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    // Defines the expected format for user date/time input.
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    // Defines the desired format for displaying the date/time to the user.
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /**
     * Constructs an Event task from user input.
     *
     * @param description The description of the event.
     * @param fromString The start date/time string.
     * @param toString The end date/time string.
     * @param priority The priority of the event.
     * @throws MonetException If any date/time string is in an invalid format.
     */
    public Event(String description, String fromString, String toString, Priority priority) throws MonetException {
        super(description, priority);
        try {
            this.from = LocalDateTime.parse(fromString.trim(), INPUT_FORMATTER);
            this.to = LocalDateTime.parse(toString.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new MonetException("Invalid date format for event. Please use 'yyyy-MM-dd HHmm'.");
        }
    }

    /**
     * Constructs an Event task when loading from the data file.
     *
     * @param description The description of the event.
     * @param from The start time as a pre-parsed LocalDateTime object.
     * @param to The end time as a pre-parsed LocalDateTime object.
     * @param priority The priority of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to, Priority priority) {
        super(description, priority);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMATTER)
                + " to: " + to.format(OUTPUT_FORMATTER) + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + priority.name()
                + " | " + description + " | " + from + " | " + to;
    }
}
