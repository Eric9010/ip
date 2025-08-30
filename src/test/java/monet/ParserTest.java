package monet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ParserTest {

    @Test
    public void parseDeadline_validInput_success() throws MonetException {
        // Test a valid input
        String input = "deadline return book /by 2025-08-30 1800";
        String[] result = Parser.parseDeadline(input);
        assertArrayEquals(new String[]{"return book", "2025-08-30 1800"}, result);
    }

    @Test
    public void parseDeadline_missingByKeyword_exceptionThrown() {
        // Test a command that is missing the "/by" keyword
        String input = "deadline return book 2025-08-30 1800";
        // Assert that executing this parse throws a MonetException
        MonetException exception = assertThrows(MonetException.class, () -> {
            Parser.parseDeadline(input);
        });
        // Check if the error message is what we expect
        assertEquals("Invalid deadline format. Use: deadline <description> /by <date>", exception.getMessage());
    }

    @Test
    public void parseDeadline_missingDescription_exceptionThrown() {
        // Test a command where the description is missing
        String input = "deadline /by 2025-08-30 1800";
        assertThrows(MonetException.class, () -> {
            Parser.parseDeadline(input);
        });
    }
}
