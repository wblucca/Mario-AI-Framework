package agents.JariwalaLuccaAgent;

import agents.robinBaumgarten.Helper;
import engine.core.MarioForwardModel;

public class HitQuestionBlock extends Task {

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        int[][] scene = model.getMarioSceneObservation(0);
        int marioX = model.getMarioScreenTilePos()[0];
        int marioY = model.getMarioScreenTilePos()[1];

        for (int i = marioX - 6; i < marioX + 6; i++) {
            for (int j = marioY - 6; j < marioY; j++) {
                if (i > 0 && i < scene.length && j > 0 && j < scene[0].length && scene[i][j] == 24) {
                    // Question block found
                    if (marioX > i) {
                        agent.setAction(Helper.createAction(true, false, false, true, false));
                    } else {
                        agent.setAction(Helper.createAction(false, true, false, true, false));
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
