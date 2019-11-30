package agents.JariwalaLuccaAgent;

import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;

public class Agent implements MarioAgent {
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
        return new boolean[0];
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
}
