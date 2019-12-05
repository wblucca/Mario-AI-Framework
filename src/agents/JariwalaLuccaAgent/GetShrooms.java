package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;

public class GetShrooms extends Task{

    public boolean run(Agent agent, MarioForwardModel model) {
        int[][] scene = model.getMarioSceneObservation(0);
        int marioX = model.getMarioScreenTilePos()[0];
        int marioY = model.getMarioScreenTilePos()[1];

        for (int i = marioX - 9; i < marioX + 9; i++) {
            for (int j = marioY - 6; j < marioY; j++) {
                if (model.getMarioMode() != 1) {
                    if (i > 0 && i < scene.length && j > 0 && j < scene[0].length && scene[i][j] == 30) {
                        // Shroom found
                        if (marioX > i) {
                            agent.setAction(Helper.createAction(true, false, false, false, true));
                        } else {
                            agent.setAction(Helper.createAction(false, true, false, false, false));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
