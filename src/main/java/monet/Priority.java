package monet;

/**
 * Represents the priority levels for a task.
 */
public enum Priority {
    HIGH, MEDIUM, LOW;
    /**
     * Converts an integer to a Priority level.
     * 1 = HIGH, 2 = MEDIUM, 3 = LOW.
     * Defaults to MEDIUM if the integer is invalid.
     *
     * @param level The priority level as an integer.
     * @return The corresponding Priority enum.
     */
    public static Priority of(int level) {
        switch (level) {
        case 1:
            return HIGH;
        case 3:
            return LOW;
        default:
            return MEDIUM; // Default to MEDIUM for 2 or any other number
        }
    }
}
