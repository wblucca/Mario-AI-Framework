package agents.JariwalaLuccaAgent;

import java.util.List;
import engine.core.MarioForwardModel;

public abstract class Task {

    // Holds the child nodes of the task (if any)
    protected List<Task> children;

    public abstract boolean run(Agent agent);
}