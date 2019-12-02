package agents.JariwalaLuccaAgent;

public class Sequence extends Task{

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
