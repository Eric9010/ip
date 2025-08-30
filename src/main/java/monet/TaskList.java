package monet;

import java.util.ArrayList;

/**
 * Contains the task list and operations to manipulate it.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

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

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public Task deleteTask(int index) {
        return this.tasks.remove(index);
    }

    /**
     * Finds and returns a list of tasks that contain the given keyword in their description.
     * The search is case-sensitive.
     *
     * @param keyword The keyword to search for within task descriptions.
     * @return A new TaskList containing only the matching tasks.
     */
    public TaskList findTasks(String keyword) {
        ArrayList<Task> foundTasksList = new ArrayList<>();

        // Iterate through every task in the list.
        for (Task task : this.tasks) {
            // Check if the task's description contains the keyword.
            if (task.getDescription().contains(keyword)) {
                foundTasksList.add(task);
            }
        }
        // Return a new TaskList composed of the found tasks.
        return new TaskList(foundTasksList);
    }
}
