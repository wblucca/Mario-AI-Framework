package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;


public class JumpOverPipe extends Task {

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        int[][] scene = model.getMarioSceneObservation();
        for (int i = 25; i < scene.length; i++) {
            for (int j = 6; j < scene[0].length; j++) {
                if (scene[i][j] == 34) {
                    agent.setAction(Helper.createAction(false, true, false, true, false));
                    return true;
                }
            }
        }
        return false;
    }
}
