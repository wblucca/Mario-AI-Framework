package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;

import java.util.Arrays;

public class Selector extends Task {

    /**
     * Construct a Selector node with the given children
     * @param tasks Child nodes
     */
    public Selector(Task... tasks) {
        children = Arrays.asList(tasks);
    }

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        // Run all children, in order, until one succeeds
        for (Task c : children) {
            if (c.run(agent, model)) {
                // Child succeeded, this child was "selected"
                return true;
            }
        }

        // All children failed, selector has failed
        return false;
    }
}
