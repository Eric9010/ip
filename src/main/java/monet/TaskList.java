package monet;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Contains the task list and provides operations to manipulate it.
 * This class encapsulates the ArrayList of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with pre-loaded tasks.
     *
     * @param tasks An ArrayList of tasks to initialize the list with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public int getSize() {
        return this.tasks.size();
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The Task object to be added to the list.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a task from the task list at a specified index.
     *
     * @param index The 0-based index of the task to be removed.
     * @return The Task object that was removed from the list.
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size()).
     */
    public Task deleteTask(int index) {
        // We assert that the index is valid. This should always be true.
        assert index >= 0 && index < this.tasks.size() : "Task index is out of bounds";
        return this.tasks.remove(index);
    }

    /**
     * Finds and returns a list of tasks that contain the given keyword in their description.
     * Uses Java Streams.
     *
     * @param keyword The keyword to search for within task descriptions.
     * @return A new TaskList containing only the matching tasks.
     */
    public TaskList findTasks(String keyword) {
        // Convert the list to a stream, filter based on the condition, and collect the results into a new list.
        ArrayList<Task> foundTasksList = this.tasks.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));
        return new TaskList(foundTasksList);
    }

    /**
     * Filters the current task list to find tasks of a specific priority level.
     * The original list is not modified.
     *
     * @param priority The priority level (e.g., HIGH, MEDIUM, LOW) to filter tasks by.
     * @return A new TaskList containing only the tasks that match the specified priority.
     */
    public TaskList filterByPriority(Priority priority) {
        ArrayList<Task> foundTasksList = this.tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toCollection(ArrayList::new));
        return new TaskList(foundTasksList);
    }

}
