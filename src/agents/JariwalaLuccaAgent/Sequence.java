package agents.JariwalaLuccaAgent;

import java.util.Arrays;
import java.util.List;
import engine.core.MarioForwardModel;

public class Sequence extends Task {

    /**
     * Construct a Sequence node with the given children
     * @param tasks List of child nodes
     */
    public Sequence(List<Task> tasks) {
        children = tasks;
    }

    /**
     * Construct a Sequence node with the given children
     * @param tasks Child nodes
     */
    public Sequence(Task... tasks) {
        this(Arrays.asList(tasks));
    }

    @Override
    public boolean run() {
        // Run all children, in order, until one fails
        for (Task c : children) {
            if (!c.run()) {
                // Child failed, whole sequence fails
                return false;
            }
        }

        // All ran successfully, sequence succeeds
        return true;
    }
}
