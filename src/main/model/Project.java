package model;

import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private String description;
    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        tasks = new ArrayList<>();
        this.description = description;
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (task == null) {
            throw new NullArgumentException("task is null");
        }
        if (!contains(task) && !task.equals(this)) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (task == null) {
            throw new NullArgumentException("task is null");
        }
        if (contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    //EFFECTS: return an integer which represents the total estimated time to complete of
    //         all todos in tasks
    public int getEstimatedTimeToComplete() {
        int p = 0;
        for (Todo t : tasks) {
            p = p + t.getEstimatedTimeToComplete();
        }
        return p;
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
    //     the percentage of completion (rounded down to the nearest integer).
    //     the value returned is the average of the percentage of completion of
    //     all the tasks and sub-projects in this project.
    public int getProgress() {
        int p = 0;
        if (getNumberOfTasks() == 0) {
            return 0;
        } else {
            for (Todo t : tasks) {
                p = p + t.getProgress();
            }
            return p / getNumberOfTasks();
        }
    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
    //     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    private class ProjectIterator implements Iterator<Todo> {
        private int priority = 1;
        private int id = 0;
        private int num = 0;


        @Override
        public boolean hasNext() {
            boolean h = id <= tasks.size() - 1 && num <= tasks.size() - 1 && priority <= 4;
            return h;
        }

        //EFFECTS: return the todo item which has the same level of priority that matches priority number,
        //         progress to the next level of priority if all todos with this priority have been returned.
        private Todo nextOne() {
            while (hasNext()) {
                if (tasks.get(id).getPriority().equals(new Priority(priority))) {
                    return tasks.get(id);
                } else {
                    id++;
                }
            }
            priority++;
            id = 0;
            return nextOne();
        }
        //REQUIRES: !hasNext is not true, or throw NoSuchElementException
        //EFFECTS: return the todo that returned by nextOne()
        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Todo todo = nextOne();
            num++;
            id++;
            if (!hasNext()) {
                priority++;
                id = 0;
            }
            return todo;
        }
    }
}
