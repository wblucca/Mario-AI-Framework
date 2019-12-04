package agents.JariwalaLuccaAgent;

import java.util.Arrays;
import java.util.List;

public class NonDeterministicSelector extends Task {

    /**
     * Construct a NonDeterministicSelector node with the given children
     *
     * @param tasks List of child nodes
     */
    public NonDeterministicSelector(List<Task> tasks) {
        children = tasks;
    }

    /**
     * Construct a NonDeterministicSelector node with the given children
     *
     * @param tasks Child nodes
     */
    public NonDeterministicSelector(Task... tasks) {
        this(Arrays.asList(tasks));
    }

    @Override
    public boolean run(Agent agent) {
        // Randomize the order of the children
        shuffleChildren();

        // Run all children, in a random order, until one succeeds
        for (Task c : children) {
            if (c.run(agent)) {
                // Child succeeded, this child was "selected"
                return true;
            }
        }

        // All children failed, selector has failed
        return false;
    }

    private void shuffleChildren() {
        int n = children.size();

        // While unshuffled elements remain
        while (n > 1) {
            // Pick random element
            int k = (int) (Math.random() * n);

            // Swap elements
            Task temp = children.get(n - 1);
            children.set(n - 1, children.get(k));
            children.set(k, temp);

            n--;
        }
    }
}
