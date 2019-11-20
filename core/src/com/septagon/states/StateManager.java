package com.septagon.states;

/*
Class that is used to manage all of the states within the game and manage the changes between
the different states
 */

import java.util.ArrayList;

public class StateManager 
{
    private ArrayList<State> states;

    public StateManager()
    {
        states = new ArrayList<State>();
    }

    public void changeState(State newState)
    {
    }

    public void removeState(State stateToRemove)
    {
    }
}
