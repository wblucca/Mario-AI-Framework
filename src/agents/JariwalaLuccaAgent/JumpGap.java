package agents.JariwalaLuccaAgent;

import engine.core.MarioForwardModel;
import agents.robinBaumgarten.Helper;

public class JumpGap extends Task{
    private MarioForwardModel model;

    public JumpGap (MarioForwardModel modelimport) {
        model = modelimport;
    }

    @Override
    public boolean run(Agent agent) {
        int[][] scene = model.getMarioSceneObservation();
        int j = 12;
        while (j < scene[0].length) {
            if (scene[0][j] != 17 || scene[1][j] != 17) {
                agent.setAction(Helper.createAction(false, true, false, true, false));
                return true;
            }
            j++;
        }
        return false;
    }
}
