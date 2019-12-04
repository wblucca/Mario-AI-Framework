package agents.JariwalaLuccaAgent;

import java.util.Arrays;
import java.util.Random;

public class HoldingSelector extends NonDeterministicSelector {

    // How long to hold the last task for
    private int holdDuration;
    // How long the current action has been held for
    private int curHold;
    // Task to run while holding
    private Task heldTask;

    /**
     * Construct a NonDeterministicSelector node with the given children
     *
     * @param tasks Child nodes
     */
    public HoldingSelector(int holdDuration, Task... tasks) {
        super(tasks);
        this.holdDuration = holdDuration;
        curHold = holdDuration;
    }

    /**
     * Construct a NonDeterministicSelector with the given children and seed
     *
     * @param seed Random generation seed
     * @param tasks Child nodes
     */
    public HoldingSelector(long seed, int holdDuration, Task... tasks) {
        super(seed, tasks);
        this.holdDuration = holdDuration;
        curHold = holdDuration;
    }

    @Override
    public boolean run(Agent agent) {
        // Randomize the order of the children
        shuffleChildren();

        if (curHold < holdDuration) {
            if (heldTask.run(agent)) {
                // Held task succeeded, keep holding
                return true;
            }
        }

        // Held task failed or timed out (exceed holdDuration)
        curHold = 0;

        // Run all children, in a random order, until one succeeds
        for (Task c : children) {
            if (c.run(agent)) {
                // Child succeeded, this child will be held
                heldTask = c;
                return true;
            }
        }

        // All children failed, selector has failed
        return false;
    }
}
