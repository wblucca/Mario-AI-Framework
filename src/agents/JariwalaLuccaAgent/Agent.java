package agents.JariwalaLuccaAgent;

import agents.robinBaumgarten.Helper;
import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;

public class Agent implements MarioAgent {
    // Current action to use
    private boolean action[] = Helper.createAction(false, false, false, false, false);
    // How many frames since last tree evaluation
    private int curInertia = 0;

    // How many frames to hold on to one action
    private static final int INERTIA = 4;



    /**
     * initialize and prepare the agent before the game starts
     *
     * @param model a forward model object so the agent can simulate or initialize some parameters based on it.
     * @param timer amount of time before the agent has to return
     */
    @Override
    public void initialize(MarioForwardModel model, MarioTimer timer) {

    }

    /**
     * get mario current actions
     *
     * @param model a forward model object so the agent can simulate the future.
     * @param timer amount of time before the agent has to return the actions.
     * @return an array of the state of the buttons on the controller
     */
    @Override
    public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
        curInertia++;

        Task root = new Selector(
                new Sequence(
                        new IsHoldingJump(model),
                        new Selector(
                                new JumpOverEnemy(model), new JumpGap(model), new JumpOverPipe(model)

                                )),
                new Walk(model));

        if (curInertia >= INERTIA) {
            // Reset inertia if tree succeeds
            if (root.run(this)) {
                curInertia = 0;
            }
        }

        if (action == null) {
            action = Helper.createAction(false, false, false, false, false);
        }
        return action;
    }

    /**
     * Return the name of the agent that will be displayed in debug purposes
     *
     * @return
     */
    @Override
    public String getAgentName() {
        return "JariwalaLuccaAgent";
    }

    public void setAction(boolean[] nextAction) {
        action = nextAction;
    }

    public boolean[] getAction() {
        return action;
    }

}