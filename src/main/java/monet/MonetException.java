package monet;

/**
 * Custom exception class for the Monet application.
 * Used for all application-specific errors, such as parsing errors or invalid user input.
 */
public class MonetException extends Exception {
    /**
     * Constructs a new MonetException with the specified detail message.
     *
     * @param message The detail message for the exception.
     */
    public MonetException(String message) {
        super(message);
    }
}
