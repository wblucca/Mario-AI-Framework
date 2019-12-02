package agents.JariwalaLuccaAgent;

import java.util.Arrays;
import java.util.List;

public class Selector extends Task {

    /**
     * Construct a Sequence node with the given children
     * @param tasks List of child nodes
     */
    public Selector(List<Task> tasks) {
        children = tasks;
    }

    /**
     * Construct a Sequence node with the given children
     * @param tasks Child nodes
     */
    public Selector(Task... tasks) {
        this(Arrays.asList(tasks));
    }

    @Override
    public boolean run() {
        // Run all children, in order, until one succeeds
        for (Task c : children) {
            if (!c.run()) {
                // Child succeeded, this child was "selected"
                return true;
            }
        }

        // All children failed, selector has failed
        return false;
    }
}
