package org.example;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Console shell around {@link TodoList}.
 *
 * <p>Supported commands: {@code add <task>}, {@code remove <index>},
 * {@code done <index>}, {@code search <text>}, {@code list},
 * {@code clear}, {@code exit}.</p>
 */
public final class TodoApp {

    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_DONE = "done";
    private static final String COMMAND_SEARCH = "search";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_HELP = "help";

    private static final String DONE_MARK = "[x]";
    private static final String NOT_DONE_MARK = "[ ]";

    private final TodoList todoList = new TodoList();

    public static void main(String[] args) {
        new TodoApp().run(new Scanner(System.in));
    }

    /**
     * Reads commands line by line until {@code exit} or the end of input.
     *
     * @param scanner source of commands
     */
    void run(Scanner scanner) {
        printHelp();
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase(Locale.ROOT);
            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case COMMAND_EXIT -> {
                    System.out.println("Bye!");
                    return;
                }
                case COMMAND_ADD -> addTask(argument);
                case COMMAND_REMOVE -> removeTask(argument);
                case COMMAND_DONE -> doneTask(argument);
                case COMMAND_SEARCH -> searchTasks(argument);
                case COMMAND_CLEAR -> clearTasks();
                case COMMAND_LIST -> printTasks();
                case COMMAND_HELP -> printHelp();
                default -> System.out.println("Unknown command: " + command + ". Type 'help' for commands.");
            }
        }
    }

    private void addTask(String task) {
        if (todoList.add(task)) {
            System.out.println("Added: " + task.trim());
        } else {
            System.out.println("Nothing to add.");
        }
    }

    private void removeTask(String argument) {
        int index = parseIndex(argument);
        if (index < 0) {
            System.out.println("Usage: remove <index>");
            return;
        }
        if (todoList.remove(index)) {
            System.out.println("Removed task #" + index);
        } else {
            System.out.println("No task with index " + index);
        }
    }

    private void doneTask(String argument) {
        int index = parseIndex(argument);
        if (index < 0) {
            System.out.println("Usage: done <index>");
            return;
        }
        if (todoList.markDone(index)) {
            System.out.println("Task #" + index + " marked as done.");
        } else {
            System.out.println("No task with index " + index);
        }
    }

    private void searchTasks(String query) {
        int index = 0;
        int found = 0;
        for (Task task : todoList.getAll()) {
            if (task.getText().toLowerCase(Locale.ROOT).contains(query.trim().toLowerCase(Locale.ROOT))) {
                System.out.println(formatLine(index, task));
                found++;
            }
            index++;
        }
        if (found == 0) {
            System.out.println("No matches.");
        }
    }

    private void clearTasks() {
        if (todoList.size() == 0) {
            System.out.println("The list is already empty.");
            return;
        }
        todoList.clear();
        System.out.println("List cleared.");
    }

    private void printTasks() {
        if (todoList.size() == 0) {
            System.out.println("The list is empty.");
            return;
        }
        int index = 0;
        for (Task task : todoList.getAll()) {
            System.out.println(formatLine(index, task));
            index++;
        }
    }

    private void printHelp() {
        System.out.println("Commands: add <task>, remove <index>, done <index>, search <text>, list, clear, exit");
    }

    private static String formatLine(int index, Task task) {
        String mark = task.isDone() ? DONE_MARK : NOT_DONE_MARK;
        return "[" + index + "] " + mark + " " + task.getText();
    }

    private int parseIndex(String argument) {
        try {
            return Integer.parseInt(argument.trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}