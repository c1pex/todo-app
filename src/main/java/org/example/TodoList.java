package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores todo tasks as plain text lines.
 *
 * <p>Values are normalized before storing: leading and trailing whitespace is
 * removed. {@code null}, empty and blank entries are ignored.</p>
 */
public class TodoList {

    private final List<String> tasks = new ArrayList<>();

    /**
     * Adds a normalized task to the list.
     *
     * @param task raw task text; {@code null} and blank values are ignored
     * @return {@code true} if the task was added
     */
    public boolean add(String task) {
        if (task == null) {
            return false;
        }
        String normalized = task.trim();
        if (normalized.isEmpty()) {
            return false;
        }
        return tasks.add(normalized);
    }

    /**
     * Removes the task at the given index.
     *
     * @param index zero based position of the task
     * @return {@code true} if a task was removed
     */
    public boolean remove(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.remove(index);
        return true;
    }

    /**
     * Returns a copy of all stored tasks, so callers cannot modify the list.
     *
     * @return new list with the current tasks
     */
    public List<String> getAll() {
        return new ArrayList<>(tasks);
    }

    /**
     * @return number of stored tasks
     */
    public int size() {
        return tasks.size();
    }
}
