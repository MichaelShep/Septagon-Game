package com.septagon.states;

/*
Child class of the State class that will manage the system when the user is in the game
 */

public class GameState extends State
{
    private int timePassed;
    private boolean paused = false;
    private int minigameScore;

    public GameState()
    {
        timePassed = 0;
        minigameScore = 0;
    }

    public void initialise()
    {
    }

    public void update()
    {
    }

    public void render()
    {
    }

    public void pauseGame() {}

    public void unpauseGame() {}

    //Getters and setters for all private attributes in the class

    public int getTimePassed() 
    {
        return timePassed;
    }

    public boolean isPaused()
    {
        return paused;
    }

    public int getMinigameScore() {
        return minigameScore;
    }

    public void setMinigameScore(int minigameScore) {
        this.minigameScore = minigameScore;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }
}
