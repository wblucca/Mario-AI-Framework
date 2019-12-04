package agents.JariwalaLuccaAgent;

import java.util.Arrays;
import engine.core.MarioForwardModel;

public class Sequence extends Task {

    /**
     * Construct a Sequence node with the given children
     * @param tasks Child nodes
     */
    public Sequence(Task... tasks) {
        children = Arrays.asList(tasks);
    }

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        // Run all children, in order, until one fails
        for (Task c : children) {
            if (!c.run(agent, model)) {
                // Child failed, whole sequence fails
                return false;
            }
        }

        // All ran successfully, sequence succeeds
        return true;
    }
}
