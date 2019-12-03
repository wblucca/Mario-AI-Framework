package agents.JariwalaLuccaAgent;

import java.util.Arrays;
import java.util.List;
import engine.core.MarioForwardModel;

public class Selector extends Task {

    /**
     * Construct a Selector node with the given children
     * @param tasks List of child nodes
     */
    public Selector(List<Task> tasks) {
        children = tasks;
    }

    /**
     * Construct a Selector node with the given children
     * @param tasks Child nodes
     */
    public Selector(Task... tasks) {
        this(Arrays.asList(tasks));
    }

    @Override
    public boolean run(Agent agent) {
        // Run all children, in order, until one succeeds
        for (Task c : children) {
            if (c.run(agent)) {
                // Child succeeded, this child was "selected"
                return true;
            }
        }

        // All children failed, selector has failed
        return false;
    }
}
