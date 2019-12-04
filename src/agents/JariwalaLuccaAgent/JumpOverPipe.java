package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;


public class JumpOverPipe extends Task {

    @Override
    public boolean run(Agent agent, MarioForwardModel model) {
        int[][] scene = model.getMarioSceneObservation();
        int[][] scene2 = model.getMarioSceneObservation(0);
            for (int j = 6; j < scene2[15].length; j++) {
                if (scene2[16][j] == 34 || scene2[15][j] == 18) {
                    agent.setAction(Helper.createAction(false, true, false, true, false));
                    return true;
                }
            }
        return false;
    }
}
