package com.septagon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.septagon.states.GameState;
import com.septagon.states.State;
import com.septagon.states.StateManager;

public class Game extends ApplicationAdapter
{
	private SpriteBatch batch;
	private State startState;
	private StateManager stateManager;
	
	@Override
	//Initialises and creates all variables and objects in the game
	public void create () 
	{
		batch = new SpriteBatch();
		
		//Intialise all variables with default values
		startState = new GameState();
		stateManager = new StateManager();
		
		//Set the current state of the game to be the GameState and 
		//initialise this state
		stateManager.changeState(startState);
		stateManager.initialise();
	}

	@Override
	//Render all objects onto the screen - called every frame
	public void render () 
	{
		//Used to clear the background of the screen to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Updates the current state of the game
		stateManager.update();
		
		//Anything between begin and end is used to render our whole game
		batch.begin();
		
		//Render the current state of the game
		stateManager.render(batch);
		
		batch.end();
		
	}
	
	@Override
	//Disposes of all objects that need disposing once the game has finished
	public void dispose () 
	{
		batch.dispose();
	}
}
