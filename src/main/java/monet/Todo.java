package monet;

/**
 * Represents a simple "To-do" task without any specific date/time.
 * It is a subclass of Task.
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo task.
     *
     * @param description The description of the todo.
     * @param priority The priority of the todo task.
     */
    public Todo(String description, Priority priority) {
        super(description, priority);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + priority.name() + " | " + description;
    }
}
