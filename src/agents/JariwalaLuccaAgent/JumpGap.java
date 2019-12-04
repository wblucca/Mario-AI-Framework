package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;

public class JumpGap extends Task{

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        int[][] scene = model.getScreenSceneObservation();
        int j = 14;
        while (j < scene[16].length) {
            if (scene[16][j] != 17) {
                agent.setAction(Helper.createAction(false, true, false, true, true));
                return true;
            }
            j++;
        }
        return false;
    }
}
