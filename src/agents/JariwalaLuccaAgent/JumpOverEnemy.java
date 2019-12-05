package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;


public class JumpOverEnemy extends Task{

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        float[] marioPos = model.getMarioFloatPos();
        float[] enemyPos = model.getEnemiesFloatPos();
        for (int i = 0; i < enemyPos.length/3; i++) {
            float enemyx = enemyPos[3 * i + 1];
            float enemyy = enemyPos[3 * i + 2];
            if (Math.abs(enemyx - marioPos[0]) < 64 && marioPos[1] - enemyy < 10) {
                if (Math.abs(enemyx - marioPos[0]) < 6 && Math.abs(marioPos[1] - enemyy) < 6) {
                    agent.setAction(Helper.createAction(true, false, false, true, false));
                }
                else {
                    agent.setAction(Helper.createAction(false, true, false, true, false));
                }
                return true;
            }
        }
        return false;
    }
}
