package edu.moravian.agentManager;

import edu.moravian.agent.ChaseAgent;
import edu.moravian.agent.PlayerAgent;

/**
 * This class always chases the player object.
 *
 * @author James Moore (moore.work@live.com)
 */
public class ChasePointState implements AgentState {

    private static PlayerAgent objective = PlayerAgent.instance();
    private ChaseStateMachine agentState;

    public ChasePointState() {
    }

    @Override
    public void Enter(ChaseAgent ag) {
        agentState = ag.getBehaviorManager();
    }

    @Override
    public void Execute(ChaseAgent ag) {

        if (agentState.isTired()) {
            agentState.setCurrent_state(new ChaseAgentSleep());
        } else if (agentState.isHungry()) {

            agentState.setCurrent_state(new EatState());
        } else {
            ag.chase(objective.getPlayerPosition());
        }

    }

    @Override
    public void Leave(ChaseAgent ag) {
    }
}
