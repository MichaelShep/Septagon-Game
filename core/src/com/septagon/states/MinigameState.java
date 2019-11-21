package com.septagon.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
Child of State class that will be used to manage the system when the user is playing the minigame
 */

public class MinigameState extends State 
{
    private int score;

    public MinigameState()
    {
        score = 0;
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

    public void handleInputForMinigame() {}

    private void returnToMainGame() {}
}