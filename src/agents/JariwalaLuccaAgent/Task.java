package agents.JariwalaLuccaAgent;

import java.util.List;

public abstract class Task {

    // Holds the child nodes of the task (if any)
    protected List<Task> children;

    public abstract boolean run();
}