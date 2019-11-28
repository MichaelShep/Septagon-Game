package com.septagon.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.septagon.game.InputManager;

/*
Child of the State class that will be used to manage the system when the user has reached game over
 */

public class GameOverState extends State
{
    private String gameOverLabel;
    private String scoreLabel;
    private String playAgainLabel;

    public GameOverState(InputManager inputManager, BitmapFont font)
    {
        super(inputManager, font, StateID.GAME_OVER);
        gameOverLabel = "";
        scoreLabel = "";
        playAgainLabel = "";
    }

    public void initialise()
    {
    }

    public void update()
    {
    }

    public void render(SpriteBatch batch)
    {
    }

    private void quitGame()
    {
    }

    private void playAgain()
    {
    }
}
