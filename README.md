# todo-app

A simple console todo list application written in **Java 23** and built with **Gradle**.

The project is developed following the **GitHub Flow** branching model: every feature
gets its own branch created from `main`, each change lands via a pull request, and
`main` is always deployable.

## Project structure

| Component      | Description                                                     |
|----------------|-----------------------------------------------------------------|
| `Task`         | Todo item model with `text` and `done` state.                   |
| `TodoList`     | In-memory task storage: add, remove, mark done, clear, search.  |
| `TodoApp`      | Console shell that exposes every `TodoList` operation.          |
| `TodoListTest` | JUnit 5 unit tests for `TodoList`.                              |

## Application commands

| Command             | Description                                       |
|---------------------|---------------------------------------------------|
| `add <task>`        | Adds a task (trims whitespace, ignores blank).    |
| `remove <index>`    | Removes the task at `index`.                      |
| `done <index>`      | Marks the task at `index` as done.                |
| `search <text>`     | Finds tasks containing `<text>` (case-insensitive). |
| `list`              | Prints all tasks with indices and `[x]`/`[ ]` marks. |
| `clear`             | Removes all tasks.                                |
| `exit`              | Quits the application.                            |

Example session:

```
> add write report
Added: write report
> done 0
Task #0 marked as done.
> list
[0] [x] write report
> exit
Bye!
```

## Building

Requires a JDK (the wrapper will use the configured Gradle distribution):

```
.\gradlew build
```

The build runs the JUnit test suite and produces a self-contained (fat) JAR:

```
.\gradlew clean build
```

Artifact output: `build/libs/todo-app-0.3.0.jar`.

## Running

```
java -jar build/libs/todo-app-0.3.0.jar
```

The version is read from `gradle.properties` (`version=0.3.0`), so the JAR name follows
the current version: `build/libs/todo-app-<version>.jar`.

## CI/CD workflows

| Workflow                             | Trigger                                              | What it does                                          |
|--------------------------------------|------------------------------------------------------|-------------------------------------------------------|
| `CI / Build & Release`               | push to `main`                                       | Builds with Gradle, runs tests, creates a GitHub Release `v<version>` and attaches `todo-app-<version>.jar`. |
| `Publish to GitHub Packages (Maven)` | after a successful `CI / Build & Release` run        | Publishes the fat JAR as `org.example:todo-app:<version>` to GitHub Packages (skips versions that already exist). |
| `Nightly Build`                      | daily at `02:00 UTC` (schedule) + manual `workflow_dispatch` | Full `clean build` sanity check; no release, no publish. |

## Links

- Repository: <https://github.com/c1pex/todo-app>
- Actions: <https://github.com/c1pex/todo-app/actions>
- Releases: <https://github.com/c1pex/todo-app/releases>
- Packages: <https://github.com/users/c1pex/packages?repo_name=todo-app>
- GitHub Packages Maven registry: <https://maven.pkg.github.com/c1pex/todo-app>