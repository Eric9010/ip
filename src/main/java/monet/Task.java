package monet;

/**
 * Represents a generic task. This is an abstract base class for specific task types
 * like Todo, Deadline, and Event.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected Priority priority;

    /**
     * Constructs a general Task from user input.
     *
     * @param description The description of the task.
     * @param priority The priority of the task.
     */
    public Task(String description, Priority priority) {
        this.description = description;
        this.isDone = false;
        this.priority = priority;
    }

    /**
     * Overloaded constructor that defaults to MEDIUM priority.
     * @param description The description of the task.
     */
    public Task(String description) {
        // By default, all tasks are created with MEDIUM priority unless specified.
        this(description, Priority.MEDIUM);
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a status icon representing whether the task is done.
     * @return "X" if the task is done, " " (space) otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X or leave it blank
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns a character representing the priority.
     * @return 'H' for HIGH, 'M' for MEDIUM, 'L' for LOW.
     */
    public char getPriorityIcon() {
        return this.priority.name().charAt(0);
    }

    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return "[" + getPriorityIcon() + "]" + "[" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns a machine-readable string representation of the task for saving to a file.
     * This method must be implemented by all subclasses.
     *
     * @return A formatted string suitable for file storage.
     */
    public abstract String toFileString();
}
