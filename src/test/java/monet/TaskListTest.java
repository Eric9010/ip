package monet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {

    @Test
    public void deleteTask_validIndex_success() throws MonetException {
        // Set up the initial state
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("task 1"));
        tasks.addTask(new Todo("task 2"));
        tasks.addTask(new Todo("task 3"));

        // Execute the method to be tested
        tasks.deleteTask(1); // Delete "task 2"

        // Assert the expected outcome
        assertEquals(2, tasks.getSize()); // Check if the size is correct
        assertEquals("[T][ ] task 3", tasks.getTask(1).toString()); // Check if "task 3" shifted to the correct index
    }

    @Test
    public void deleteTask_invalidIndex_exceptionThrown() {
        // Set up
        TaskList tasks = new TaskList();
        tasks.addTask(new Todo("task 1"));

        // Assert that trying to delete a task at an out-of-bounds index
        // throws the expected exception from the underlying ArrayList
        assertThrows(IndexOutOfBoundsException.class, () -> {
            tasks.deleteTask(1); // Index 1 is out of bounds for a list of size 1
        });
    }
}
