package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;

public class Walk extends Task{

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        if (model.isMarioOnGround()) {
            agent.setAction(Helper.createAction(false, true, false, false, true));
            return true;
        }
        else {
            return false;
        }
    }
}
