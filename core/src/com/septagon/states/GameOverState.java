package com.septagon.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
Child of the State class that will be used to manage the system when the user has reached game over
 */

public class GameOverState extends State
{
    private String gameOverLabel;
    private String scoreLabel;
    private String playAgainLabel;

    public GameOverState()
    {
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