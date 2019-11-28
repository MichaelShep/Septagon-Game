package com.septagon.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.septagon.game.InputManager;

/*
Abstract class that defines what all states within the game should contain
 */

public abstract class State
{
    protected InputManager inputManager;
    protected BitmapFont font;

    protected State(InputManager inputManager, BitmapFont font)
    {
        this.inputManager = inputManager;
        this.font = font;
    }

    public abstract void initialise();
    public abstract void update();
    public abstract void render(SpriteBatch batch);
}
