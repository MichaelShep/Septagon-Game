package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.septagon.entites.Engine;
import com.septagon.entites.Tile;
import com.septagon.entites.TiledGameMap;
import com.septagon.game.InputManager;
import com.septagon.game.Player;

import java.util.ArrayList;

/*
Child class of the State class that will manage the system when the user is in the game
 */

public class GameState extends State
{
    // we will use 32px/unit in world
    public final static float SCALE = 32f;
    public final static float INV_SCALE = 1.f/SCALE;
    // this is our "target" resolution, note that the window can be any size, it is not bound to this one
    public final static float VP_WIDTH = 640 * INV_SCALE;
    public final static float VP_HEIGHT = 480 * INV_SCALE;

	//Camera that control the viewport of the game depending on input
    private OrthographicCamera camera;
    //Viewport that is used alongside the camera that contains the whole game map
    private ExtendViewport viewport;
    //Renderer for drawing all none textured items to the screen
    private ShapeRenderer shapes;
    //Viewport that is used for elements that should stay on the screen at all times
    private FitViewport shapeViewport;
    private Stage shapeStage;
    //Spritebatch that is used for renderering all objects in the game
    private SpriteBatch batch;
    private SpriteBatch objectBatch;

    //Contains all the information about our game map
	private TiledGameMap gameMap;
	
    private int timePassed;
    private boolean paused = false;
    private int minigameScore;

    //Loads textures initiates engines with these textures
    private Texture engineTexture1 = new Texture(Gdx.files.internal("images/engine1.png"));
    private Texture engineTexture2 = new Texture(Gdx.files.internal("images/engine2.png"));
    private Engine engine1;
    private Engine engine2;

    private float currentCameraX, currentCameraY;

    //Creates player class to contain list of engines
    private Player player = new Player();

    private ArrayList<Tile> tiles = new ArrayList<Tile>();



    //Constructor that initialises all neccessary variables and also takes in all required values from the game
    public GameState(InputManager inputManager, BitmapFont font, SpriteBatch batch, OrthographicCamera camera)
    {
        super(inputManager, font, StateID.GAME);
    	this.camera = camera;
    	this.batch = batch;
        timePassed = 0;
        minigameScore = 0;
        currentCameraX = 0;
        currentCameraY = 0;
    }

    //Sets up all the objects in our game
    public void initialise()
    {
        //Initialises all engines in the game
        engine1 = new Engine(0,0,64,64, engineTexture1,'U', 10, 2, 4, "Friendly", 1, 'U', 20, 20, 4, 01);
        engine2 = new Engine(35,35,64,64, engineTexture2,'U', 10, 2, 4, "Friendly", 1, 'U', 20, 20, 4, 02);

        //Adds all the engines to the player class's list of engines
        player.addEngine(engine1);
        player.addEngine(engine2);

        // Intialises the game viewport
        viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);

        // ShapeRenderer so we can see our touch point
        shapes = new ShapeRenderer();
        shapeViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeStage = new Stage(shapeViewport, batch);
        shapes.setProjectionMatrix(shapeStage.getCamera().combined);

        //This batch is used to render the objects (Engines, ET Fortresses) on top of the map
        objectBatch = new SpriteBatch();

        //Creates and initialises the game map
        gameMap = new TiledGameMap();
        gameMap.initialise();

        //Moves the camera to its starting position and makes sure the screen gets updated after this
        camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        objectBatch.setProjectionMatrix(camera.combined);

        //Create objects referring to all tiles in game
        for(int y = 0; y < gameMap.getMapHeight(); y++)
        {
            for(int x = 0; x < gameMap.getMapWidth(); x++)
            {
                if(gameMap.getTileByCoordinate(0, x, y) != null)
                    tiles.add(gameMap.getTileByCoordinate(0, x, y));
            }
        }
    }

    //Update all objects in the game
    public void update()
    {
    	gameMap.update();
    	player.update();
    	currentCameraX = camera.position.x;
    	currentCameraY = camera.position.y;
    }

    public void touchedTile(float x, float y)
    {
        for(Tile t: tiles)
        {
            t.checkIfIntersectedWith(x, y);
        }
    }

    public void render(SpriteBatch batch)
    {
    	//Clear the background to red
    	Gdx.gl.glClearColor(1, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    	//Render the map for our game
    	gameMap.render(camera);

    	//render movement grid if needed
        if (inputManager.isTouched()){
            this.renderMovementGrid(inputManager.getXCoord(), inputManager.getYCoord());
        }

        //Render engines
        objectBatch.setProjectionMatrix(camera.combined);
        objectBatch.begin();
        player.render(objectBatch);
        objectBatch.end();

    }


    public void renderMovementGrid(float x, float y){
            if ((x >= engine1.getX() && x <= engine1.getX() + 64) &&
                    (camera.viewportHeight - y >= engine1.getY() &&
                            camera.viewportHeight - y <= engine1.getY() + 64)){
                shapes.begin(ShapeRenderer.ShapeType.Line);
                shapes.setColor(0, 0, 1, 1);
                shapes.rect(inputManager.getXCoord(), Gdx.graphics.getHeight() - inputManager.getYCoord() - 32, 32, 96);
                shapes.rect(inputManager.getXCoord() - 32, camera.viewportHeight - inputManager.getYCoord(), 96, 32);
                shapes.end();
            }else{
                shapes.begin(ShapeRenderer.ShapeType.Line);
                shapes.setColor(0, 1, 1, 1);
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

    public float getCurrentCameraX()
    {
        return currentCameraX;
    }

    public float getCurrentCameraY()
    {
        return currentCameraY;
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

    public int getMapWidth() { return gameMap.getMapWidth(); }
    public int getMapHeight() { return gameMap.getMapHeight(); }
}
