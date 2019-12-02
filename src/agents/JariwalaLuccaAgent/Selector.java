package agents.JariwalaLuccaAgent;

public class Selector extends Task {

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
