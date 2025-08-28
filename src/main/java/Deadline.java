import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected LocalDateTime by;

    // A formatter to parse the user's input, e.g., "2025-12-02 1800"
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    // A formatter to display the date in a user-friendly way
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    public Deadline(String description, String byString) throws MonetException {
        super(description);
        try {
            this.by = LocalDateTime.parse(byString.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new MonetException("Invalid date format for deadline. Please use 'yyyy-MM-dd HHmm'.");
        }
    }

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMATTER) + ")";
    }

    @Override
    public String toFileString() {
        // Saves the date in standard ISO format, e.g., "2025-12-02T18:00"
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}