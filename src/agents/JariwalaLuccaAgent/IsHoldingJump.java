package agents.JariwalaLuccaAgent;

import agents.robinBaumgarten.Helper;
import engine.core.MarioForwardModel;

public class IsHoldingJump extends Task {


    private MarioForwardModel model;

    public IsHoldingJump(MarioForwardModel modelimport) {
        model = modelimport;
    }

    @Override
    public boolean run(Agent agent) {
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
