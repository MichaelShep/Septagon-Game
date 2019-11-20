package com.septagon.states;

/*
Abstract class that defines what all states within the game should contain
 */

public abstract class State
{
    public abstract void initialise();
    public abstract void update();
    public abstract void render();
}
