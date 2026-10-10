package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TodoListTest {

    private TodoList todoList;

    @BeforeEach
    void setUp() {
        todoList = new TodoList();
    }

    @Test
    @DisplayName("add() trims leading and trailing whitespace")
    void addNormalizesWhitespace() {
        assertTrue(todoList.add("  buy milk  "));

        List<Task> tasks = todoList.getAll();
        assertEquals(1, tasks.size());
        assertEquals("buy milk", tasks.get(0).getText());
    }

    @Test
    @DisplayName("add() keeps inner spaces but trims the edges")
    void addKeepsInnerSpaces() {
        todoList.add("\t wash the  car \n");

        assertEquals("wash the  car", todoList.getAll().get(0).getText());
    }

    @Test
    @DisplayName("add() ignores null, empty and blank values")
    void addIgnoresEmptyValues() {
        assertFalse(todoList.add(null));
        assertFalse(todoList.add(""));
        assertFalse(todoList.add("   "));
        assertFalse(todoList.add("\t \n"));
        assertEquals(0, todoList.size());
    }

    @Test
    @DisplayName("add() stores every valid task and size() follows it")
    void addIncreasesSize() {
        todoList.add("first");
        todoList.add("second");

        assertEquals(2, todoList.size());
        assertEquals(List.of("first", "second"), textsOf(todoList.getAll()));
    }

    @Test
    @DisplayName("remove() deletes an existing task and returns true")
    void removeExistingIndex() {
        todoList.add("first");
        todoList.add("second");
        todoList.add("third");

        assertTrue(todoList.remove(1));
        assertEquals(2, todoList.size());
        assertEquals(List.of("first", "third"), textsOf(todoList.getAll()));
    }

    @Test
    @DisplayName("remove() keeps tasks untouched when index is out of bounds")
    void removeOutOfBoundsIndex() {
        todoList.add("first");
        todoList.add("second");

        assertFalse(todoList.remove(-1));
        assertFalse(todoList.remove(2));
        assertFalse(todoList.remove(100));
        assertEquals(2, todoList.size());
        assertEquals(List.of("first", "second"), textsOf(todoList.getAll()));
    }

    @Test
    @DisplayName("remove() on an empty list always returns false")
    void removeOnEmptyList() {
        assertFalse(todoList.remove(0));
        assertFalse(todoList.remove(-5));
        assertEquals(0, todoList.size());
    }

    @Test
    @DisplayName("getAll() returns an independent copy of the list")
    void getAllReturnsCopy() {
        todoList.add("first");

        List<Task> copy = todoList.getAll();
        copy.clear();

        assertEquals(1, todoList.size());
        assertEquals(List.of("first"), textsOf(todoList.getAll()));
    }

    @Test
    @DisplayName("getAll() keeps insertion order")
    void getAllKeepsOrder() {
        todoList.add("alpha");
        todoList.add("beta");
        todoList.add("gamma");

        assertEquals(List.of("alpha", "beta", "gamma"), textsOf(todoList.getAll()));
    }

    @Test
    @DisplayName("clear() removes all tasks")
    void clearRemovesAllTasks() {
        todoList.add("first");
        todoList.add("second");

        todoList.clear();

        assertEquals(0, todoList.size());
        assertTrue(todoList.getAll().isEmpty());
    }

    @Test
    @DisplayName("clear() on an empty list is a no-op")
    void clearOnEmptyList() {
        todoList.clear();

        assertEquals(0, todoList.size());
    }

    @Test
    @DisplayName("markDone() marks only the requested task")
    void markDoneMarksOnlyTargetTask() {
        todoList.add("first");
        todoList.add("second");
        todoList.add("third");

        assertTrue(todoList.markDone(1));

        List<Task> tasks = todoList.getAll();
        assertFalse(tasks.get(0).isDone());
        assertTrue(tasks.get(1).isDone());
        assertFalse(tasks.get(2).isDone());
        assertEquals(3, todoList.size());
    }

    @Test
    @DisplayName("markDone() returns false for an invalid index")
    void markDoneInvalidIndex() {
        todoList.add("first");

        assertFalse(todoList.markDone(-1));
        assertFalse(todoList.markDone(1));
        assertFalse(todoList.markDone(100));
        assertFalse(todoList.getAll().get(0).isDone());
    }

    @Test
    @DisplayName("search() matches substrings ignoring character case")
    void searchIsCaseInsensitive() {
        todoList.add("Buy milk");
        todoList.add("Write report");
        todoList.add("buy bread");

        List<Task> matches = todoList.search("MILK");
        assertEquals(List.of("Buy milk"), textsOf(matches));

        matches = todoList.search("buy");
        assertEquals(List.of("Buy milk", "buy bread"), textsOf(matches));
    }

    @Test
    @DisplayName("search() returns an empty list when nothing matches")
    void searchNoResults() {
        todoList.add("Buy milk");
        todoList.add("Write report");

        assertTrue(todoList.search("zzz").isEmpty());
    }

    @Test
    @DisplayName("search() with a blank or null query matches nothing")
    void searchWithBlankOrNullQuery() {
        todoList.add("Buy milk");

        assertTrue(todoList.search("").isEmpty());
        assertTrue(todoList.search("   ").isEmpty());
        assertTrue(todoList.search(null).isEmpty());
        assertEquals(1, todoList.size());
    }

    private static List<String> textsOf(List<Task> tasks) {
        return tasks.stream().map(Task::getText).toList();
    }
}