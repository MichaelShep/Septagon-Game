package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.septagon.entites.Tile;
import com.septagon.entites.TileType;
import com.septagon.entites.TiledGameMap;
import com.septagon.game.InputManager;

/*
Child class of the State class that will manage the system when the user is in the game
 */

public class GameState extends State
{
    // we will use 32px/unit in world
    public final static float SCALE = 32f;
    public final static float INV_SCALE = 1.f/SCALE;
    // this is our "target" resolution, not that the window can be any size, it is not bound to this one
    public final static float VP_WIDTH = 640 * INV_SCALE;
    public final static float VP_HEIGHT = 480 * INV_SCALE;

	//Camera that control the viewport of the game depending on input
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private ShapeRenderer shapes;
    private FitViewport shapeViewport;
    private Stage stage;
    private SpriteBatch batch;
	
	private TiledGameMap gameMap;
	
    private int timePassed;
    private boolean paused = false;
    private int minigameScore;

    public GameState(InputManager inputManager, BitmapFont font, SpriteBatch batch, OrthographicCamera camera)
    {
        super(inputManager, font, StateID.GAME);
    	this.camera = camera;
    	this.batch = batch;
        timePassed = 0;
        minigameScore = 0;
    }

    public void initialise()
    {
        // pick a viewport that suits your thing, ExtendViewport is a good start
        viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
        // ShapeRenderer so we can see our touch point
        shapes = new ShapeRenderer();
        shapeViewport = new FitViewport(640, 480);
        stage = new Stage(shapeViewport, batch);

        gameMap = new TiledGameMap();
    	//Initialises the tileType hash map
    	TileType.setupTileTypeMap();
    	gameMap.initialise();
    }

    public void update()
    {
    	gameMap.update();
    }

    public void render(SpriteBatch batch)
    {
    	//Clear the background to red
    	Gdx.gl.glClearColor(1, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    	//Render the map for our game
    	gameMap.render(camera);

        batch.setProjectionMatrix(stage.getCamera().combined);
        if (inputManager.isTouched())
        {
            shapes.begin(ShapeRenderer.ShapeType.Line);
            shapes.setColor(0, 0, 1, 1);
            shapes.rect(inputManager.getXCoord(), Gdx.graphics.getHeight() - inputManager.getYCoord() - 32, 32, 96);
            shapes.rect(inputManager.getXCoord() - 32, camera.viewportHeight - inputManager.getYCoord(), 96, 32);
            shapes.end();
        }

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
