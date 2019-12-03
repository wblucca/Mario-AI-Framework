package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;

public class Walk extends Task{

    private MarioForwardModel model;

    public Walk (MarioForwardModel modelimport) {
        model = modelimport;
    }

    @Override
    public boolean run(Agent agent) {
        //MarioForwardModel model = null;
        //int[][] info = model.getMarioEnemiesObservation(0);
        agent.setAction(Helper.createAction(false, true, false, false, false));
        return true;
    }
}
