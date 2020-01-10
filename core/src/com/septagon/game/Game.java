package com.septagon.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.septagon.states.GameState;
import com.septagon.states.MenuState;
import com.septagon.states.State;
import com.septagon.states.StateManager;

public class Game extends ApplicationAdapter
{
	private SpriteBatch batch;
	private State startState;
	private StateManager stateManager;
	private OrthographicCamera camera;
	private BitmapFont font;

	private InputManager inputManager;



	@Override
	//Initialises and creates all variables and objects in the game
	public void create ()
	{
		batch = new SpriteBatch();

		//Set up the games camera and make it so that y = 0 is at the bottom of the screen
		//not the top
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0, 0, 0);
		camera.update();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GameFont.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
		parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:-";
		font = generator.generateFont(parameter);
		generator.dispose();

		//Intialise all variables with default values
		startState = new MenuState(inputManager, font);
		stateManager = new StateManager();

		inputManager = new InputManager(camera, stateManager, font, batch);
		Gdx.input.setInputProcessor(inputManager);

		//Set the current state of the game to be the GameState and 
		//initialise this state
		stateManager.changeState(startState);
		stateManager.initialise();
	}

	public void resize(int width, int height)
	{
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		if(stateManager.getCurrentState().getID() == State.StateID.GAME)
		{
			GameState state = (GameState) stateManager.getCurrentState();
			state.hasResized();
		}
		camera.update();
	}

	@Override
	//Render all objects onto the screen - called every frame
	public void render ()
	{
		//Updates the current state of the game
		stateManager.update();

		//Used to clear the background of the screen to black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
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
		font.dispose();
	}
}