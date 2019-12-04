package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;

import java.util.LinkedList;
import java.util.Random;

public class Chance extends Task {

    // Random generator can take a specific seed or use random one
    Random random;
    // Probability that the child task will succeed
    private double probability;
    // Decorated node to run on probability success
    private Task child;

    /**
     * Construct a Chance decorator node for the given child task
     *
     * @param probability Chance that this task will succeed and run child
     * @param child Task to run on success
     */
    public Chance(double probability, Task child) {
        this.probability = probability;
        this.child = child;
        random = new Random();
    }

    /**
     * Construct a Chance decorator node with the given seed
     *
     * @param seed Random generation seed
     * @param probability Chance that this task will succeed and run child
     * @param child Task to run on success
     */
    public Chance(long seed, double probability, Task child) {
        this.probability = probability;
        this.child = child;
        random = new Random(seed);
    }

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        if (random.nextDouble() < probability) {
            // Random chance successful
            child.run(agent, model);
            return true;
        }

        // Random chance failed
        return false;
    }

}
