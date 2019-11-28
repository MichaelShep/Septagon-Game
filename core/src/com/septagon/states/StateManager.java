package com.septagon.states;

/*
Class that is used to manage all of the states within the game and manage the changes between
the different states
 */

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StateManager 
{
    private ArrayList<State> states;
    private int currentIndex = 0;

    public StateManager()
    {
        states = new ArrayList<State>();
    }
    
    public void initialise()
    {
    	states.get(currentIndex).initialise();
    }
    
    public void update()
    {
    	states.get(currentIndex).update();
    }
    
    public void render(SpriteBatch batch)
    {
    	states.get(currentIndex).render(batch);
    }

    public void changeState(State newState)
    {
        newState.initialise();
    	states.add(newState);
    	currentIndex = states.indexOf(newState);
    }

    public void removeState(State stateToRemove)
    {
    }
    
    public int getCurrentIndex() { return currentIndex; }
    public State getCurrentState() { return states.get(currentIndex); }
}
