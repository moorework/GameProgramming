package edu.moravian.creep.AI;

import edu.moravian.creep.Creep;

/**
 *
 * @author James Moore (moore.work@live.com)
 */
public class CreepStateMachine
{

    Creep myCreep;
    private CreepState current_state;
    private CreepState previous_state;

    public void update()
    {
        /*
         * The problem we face here is that changing states is gonna get really 
         * messy if we keep it here 
         */
        current_state.Execute(myCreep);
    }

    public void RevertToPreviousState()
    {
        current_state = previous_state;
    }
}
