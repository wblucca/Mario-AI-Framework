package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;


public class JumpOverPipe extends Task {

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        int[][] scene = model.getMarioSceneObservation(0);
            for (int j = 6; j < scene[15].length; j++) {
                if (scene[15][j] == 34 || scene[16][j] == 34 || scene[15][j] == 18) {
                    agent.setAction(Helper.createAction(false, true, false, true, false));
                    return true;
                }
            }
        return false;
    }
}
