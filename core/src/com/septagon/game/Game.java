package com.septagon.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.septagon.states.GameState;
import com.septagon.states.State;
import com.septagon.states.StateManager;

public class Game extends ApplicationAdapter implements InputProcessor
{
	private SpriteBatch batch;
	private State startState;
	private StateManager stateManager;

	// we will use 32px/unit in world
	public final static float SCALE = 32f;
	public final static float INV_SCALE = 1.f/SCALE;
	// this is our "target" resolution, not that the window can be any size, it is not bound to this one
	public final static float VP_WIDTH = 640 * INV_SCALE;
	public final static float VP_HEIGHT = 480 * INV_SCALE;


	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private ShapeRenderer shapes;
	private FitViewport shapeViewport;
	private Stage stage;

	private Boolean touched = false;
	private int xCoord;
	private int yCoord;


	
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
		// pick a viewport that suits your thing, ExtendViewport is a good start
		viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
		// ShapeRenderer so we can see our touch point
		shapes = new ShapeRenderer();
		shapeViewport = new FitViewport(640, 480);
		stage = new Stage(shapeViewport, batch);
		Gdx.input.setInputProcessor(this);
		
		//Intialise all variables with default values
		startState = new GameState(camera);
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
		
		batch.setProjectionMatrix(camera.combined);
		//Anything between begin and end is used to render our whole game
		batch.begin();

		
		//Render the current state of the game
		stateManager.render(batch);
		
		batch.end();

		batch.setProjectionMatrix(stage.getCamera().combined);
		if (touched){
			shapes.begin(ShapeRenderer.ShapeType.Filled);
			shapes.setColor(0, 0, 1, 0);
			shapes.rect(xCoord, Gdx.graphics.getHeight() - yCoord - 32, 32, 96);
			shapes.rect(xCoord - 32, camera.viewportHeight - yCoord, 96, 32);
			shapes.end();
		}


	}
	
	@Override
	//Disposes of all objects that need disposing once the game has finished
	public void dispose () 
	{
		batch.dispose();
	}


	Vector3 tp = new Vector3();
	boolean dragging;
	@Override public boolean mouseMoved (int screenX, int screenY) {
		// we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
		return false;
	}

	@Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		// ignore if its not left mouse button or first touch pointer
		if (button != Input.Buttons.LEFT || pointer > 0) return false;
		//camera.unproject(tp.set(screenX, screenY, 0));
		touched = !touched;
		xCoord = Gdx.input.getX();
		yCoord = Gdx.input.getY();
		dragging = true;
		return true;
	}

	@Override public boolean touchDragged (int screenX, int screenY, int pointer) {
		if (!dragging) return false;
		touched = false;
		camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
		camera.update();
		camera.unproject(tp.set(screenX, screenY, 0));
		return true;
	}

	@Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if (button != Input.Buttons.LEFT || pointer > 0) return false;
		camera.unproject(tp.set(screenX, screenY, 0));
		dragging = false;
		return true;
	}

	/*@Override public void resize (int width, int height) {
		// viewport must be updated for it to work properly
		viewport.update(width, height, true);
	}*/


	@Override public boolean keyDown (int keycode) {
		return false;
	}

	@Override public boolean keyUp (int keycode) {
		return false;
	}

	@Override public boolean keyTyped (char character) {
		return false;
	}

	@Override public boolean scrolled (int amount) {
		return false;
	}
}


