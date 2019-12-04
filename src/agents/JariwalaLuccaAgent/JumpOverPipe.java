package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;


public class JumpOverPipe extends Task{

    private MarioForwardModel model;

    public JumpOverPipe (MarioForwardModel modelimport) {
        model = modelimport;
    }

    @Override
    public boolean run(Agent agent) {
        int[][] scene = model.getMarioSceneObservation();
        int j = 6;
        while (j < scene[0].length) {
            if (scene[25][j] == 34 || scene[26][j] == 34) {
                agent.setAction(Helper.createAction(false, true, false, true, false));
                return true;
            }
            j++;
        }
        return false;
    }
}
