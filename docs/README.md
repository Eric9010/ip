# Monet User Guide 🤖🎨

Welcome to **Monet**\! Monet is a personal assistant chatbot that helps you manage your tasks, deadlines, and events 
directly from the command line. 
Its clean, text-based interface is designed for speed and efficiency, accompanied by a host of helpful features
and beautiful art.

### Quick Start

1.  Download the `monet.jar` file from the project's 
    [releases page](https://www.google.com/search?q=https://github.com/Eric9010/ip/releases).
2.  Place the JAR file in an empty folder.
3.  Open a command prompt or terminal in that folder.
4.  Run the application with the following command:
    ```
    java -jar "monet.jar"
    ```

-----

## Features and Commands

Monet understands a variety of commands to help you manage your tasks.

### Adding a Task

You can add three types of tasks: **To-Dos**, **Deadlines**, and **Events**. 
You can also assign a priority to any task when you create it.

**A Note on Priority:**
You can optionally set a priority for any new task using the `/p <level>` flag at the end of the command.

* `/p 1`: **High** Priority
* `/p 2`: **Medium** Priority
* `/p 3`: **Low** Priority

If you don't specify a priority, it will default to **Medium**.

#### **`todo`**

Adds a simple to-do task that doesn't have a specific date or time.

* **Format:** `todo <description> [/p <level>]`
* **Example:** `todo read book /p 3`

#### **`deadline`**

Adds a task that needs to be done before a specific date and time.

* **Format:** `deadline <description> /by <yyyy-MM-dd HHmm> [/p <level>]`
* **Example:** `deadline submit report /by 2025-10-15 2359 /p 1`

#### **`event`**

Adds a task that has a specific start and end time.

* **Format:** `event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm> [/p <level>]`
* **Example:** `event team meeting /from 2025-09-15 1400 /to 2025-09-15 1600 /p 1`

-----

### Managing Your Tasks

These commands help you view and modify your existing tasks.

#### **`list`**

Displays all the tasks currently in your list.

* **Format:** `list`
* **Example:** `list`

#### **`mark` and `unmark`**

Mark a task as complete or incomplete. The `<task_number>` corresponds to the number shown in the `list` command.

* **Format:** `mark <task_number>` or `unmark <task_number>`
* **Example:** `mark 2`

#### **`delete`**

Removes a task from your list permanently.

* **Format:** `delete <task_number>`
* **Example:** `delete 3`

-----

### Finding Tasks

If your list gets long, you can search for tasks by keyword or filter them by priority.

#### **`find`**

Searches for all tasks whose description contains a specific keyword.

* **Format:** `find <keyword>`
* **Example:** `find book`

#### **`priority`**

Displays all tasks that match a given priority level.

* **Format:** `priority <level>` (1=High, 2=Medium, 3=Low)
* **Example:** `priority 1`

-----

### Exiting the Program

#### **`bye`**

Exits the Monet chatbot.

-----

## Command Summary

Here is a quick reference table for all available commands:

| Command | Format | Example |
| :--- | :--- | :--- |
| `todo` | `todo <description> [/p <level>]` | `todo read book /p 3` |
| `deadline` | `deadline <desc> /by <date> [/p <level>]` | `deadline return book /by 2025-12-02 1800` |
| `event` | `event <desc> /from <date> /to <date> [/p <level>]`| `event project meeting /from 2025-09-15 1400 /to 1600 /p 1` |
| `list` | `list` | `list` |
| `mark` | `mark <task_number>` | `mark 1` |
| `unmark` | `unmark <task_number>` | `unmark 2` |
| `delete` | `delete <task_number>` | `delete 1` |
| `find` | `find <keyword>` | `find report` |
| `priority` | `priority <level>` | `priority 1` |
| `bye` | `bye` | `bye` |