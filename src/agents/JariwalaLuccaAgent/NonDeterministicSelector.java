package agents.JariwalaLuccaAgent;

import java.util.Arrays;
import java.util.Random;

public class NonDeterministicSelector extends Task {

    // Random generator can take a specific seed or use random one
    Random random;
    /**
     * Construct a NonDeterministicSelector node with the given children
     *
     * @param tasks Child nodes
     */
    public NonDeterministicSelector(Task... tasks) {
        children = Arrays.asList(tasks);
        random = new Random();
    }

    /**
     * Construct a NonDeterministicSelector with the given children and seed
     *
     * @param seed Random generation seed
     * @param tasks Child nodes
     */
    public NonDeterministicSelector(long seed, Task... tasks) {
        children = Arrays.asList(tasks);
        random = new Random(seed);
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

    protected void shuffleChildren() {
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
