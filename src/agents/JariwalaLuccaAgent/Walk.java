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
        float[] marioPos = model.getMarioFloatPos();
        float[] enemyPos = model.getEnemiesFloatPos();
        for (int i = 0; i < enemyPos.length-2; i++) {
            float enemyx = enemyPos[3 * i + 1];
            float enemyy = enemyPos[3 * i + 2];
            if (Math.abs(enemyx) - marioPos[0] < 100) {
                agent.setAction(Helper.createAction(false, true, false, true, false));
            }
            else {
                agent.setAction(Helper.createAction(false, true, false, false, false));
            }
        }
        return true;
    }
}
