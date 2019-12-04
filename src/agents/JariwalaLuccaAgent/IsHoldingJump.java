package agents.JariwalaLuccaAgent;

import agents.robinBaumgarten.Helper;
import engine.core.MarioForwardModel;

public class IsHoldingJump extends Task {

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        // If agent is holding jump button and grounded
        if (agent.getAction()[4] && model.isMarioOnGround()) {
            // Needed to release the jump button, fails
            agent.setAction(Helper.createAction(false, false, false, false, false));
            return false;
        }
        // Didn't need to change action, succeeds
        return true;
    }
}
