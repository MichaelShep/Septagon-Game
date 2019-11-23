package com.septagon.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
Abstract class that defines what all states within the game should contain
 */

public abstract class State
{	
    public abstract void initialise();
    public abstract void update();
    public abstract void render(SpriteBatch batch);
}
