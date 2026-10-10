package org.example;

import java.util.Objects;

/**
 * A single todo item with a text and a completion state.
 */
public final class Task {

    private final String text;
    private boolean done;

    /**
     * @param text task text, expected to be already normalized
     */
    public Task(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void markDone() {
        this.done = true;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Task task)) {
            return false;
        }
        return done == task.done && Objects.equals(text, task.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, done);
    }

    @Override
    public String toString() {
        return text;
    }
}