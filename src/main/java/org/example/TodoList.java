package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Stores todo tasks as {@link Task} objects.
 *
 * <p>Values are normalized before storing: leading and trailing whitespace is
 * removed. {@code null}, empty and blank entries are ignored.</p>
 */
public class TodoList {

    private final List<Task> tasks = new ArrayList<>();

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
        return tasks.add(new Task(normalized));
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
     * Marks the task at the given index as done.
     *
     * @param index zero based position of the task
     * @return {@code true} if a task was marked
     */
    public boolean markDone(int index) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        tasks.get(index).markDone();
        return true;
    }

    /**
     * Removes every stored task.
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Returns tasks whose text contains the query, ignoring character case.
     *
     * @param query search text; {@code null} and blank values match nothing
     * @return new list with the matching tasks
     */
    public List<Task> search(String query) {
        if (query == null) {
            return List.of();
        }
        String needle = query.trim();
        if (needle.isEmpty()) {
            return List.of();
        }
        List<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getText().toLowerCase(Locale.ROOT).contains(needle.toLowerCase(Locale.ROOT))) {
                matches.add(task);
            }
        }
        return matches;
    }

    /**
     * Returns a copy of all stored tasks, so callers cannot modify the list.
     *
     * @return new list with the current tasks
     */
    public List<Task> getAll() {
        return new ArrayList<>(tasks);
    }

    /**
     * @return number of stored tasks
     */
    public int size() {
        return tasks.size();
    }
}