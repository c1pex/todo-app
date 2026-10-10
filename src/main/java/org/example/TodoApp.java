package org.example;

import java.util.Locale;
import java.util.Scanner;

/**
 * Console shell around {@link TodoList}.
 *
 * <p>Supported commands: {@code add <task>}, {@code remove <index>},
 * {@code list}, {@code exit}.</p>
 */
public final class TodoApp {

    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_REMOVE = "remove";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_HELP = "help";

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

    private void printTasks() {
        if (todoList.size() == 0) {
            System.out.println("The list is empty.");
            return;
        }
        int index = 0;
        for (String task : todoList.getAll()) {
            System.out.println("[" + index + "] " + task);
            index++;
        }
    }

    private void printHelp() {
        System.out.println("Commands: add <task>, remove <index>, list, exit");
    }

    private int parseIndex(String argument) {
        try {
            return Integer.parseInt(argument.trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
