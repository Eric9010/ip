package monet;

/**
 * Represents the set of valid commands for the Monet chatbot.
 */
public enum Command {
    LIST,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    BYE,
    UNKNOWN // Represents an invalid command
}