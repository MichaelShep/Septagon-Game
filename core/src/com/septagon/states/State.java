package com.septagon.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.septagon.game.InputManager;

/*
Abstract class that defines what all states within the game should contain
 */

public abstract class State
{
    public enum StateID { GAME_OVER, GAME, HELP, MENU, MINIGAME, SETTINGS };

    protected StateID id;
    protected InputManager inputManager;
    protected BitmapFont font;

    protected State(InputManager inputManager, BitmapFont font, StateID id)
    {
        this.inputManager = inputManager;
        this.font = font;
        this.id = id;
    }

    public abstract void initialise();
    public abstract void update();
    public abstract void render(SpriteBatch batch);

    public StateID getID() { return id; }
}
