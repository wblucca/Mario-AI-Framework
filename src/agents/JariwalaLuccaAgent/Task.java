package agents.JariwalaLuccaAgent;

import java.util.LinkedList;

public abstract class Task {

    // Holds the child nodes of the task (if any)
    protected LinkedList<Task> children;

    public abstract boolean run();
}