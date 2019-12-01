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
import com.septagon.entites.TileType;
import com.septagon.entites.TiledGameMap;
import com.septagon.game.InputManager;
import com.septagon.game.Player;

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

    //Loads textures initiates engines with these textures
    private Texture engineTexture1 = new Texture(Gdx.files.internal("images/engine1.png"));
    private Texture engineTexture2 = new Texture(Gdx.files.internal("images/engine2.png"));
    private Engine engine1 = new Engine(0,0,64,64, engineTexture1,'U', 10, 2, 4, "Friendly", 1, 'U', 20, 20, 4, 01);
    private Engine engine2 = new Engine(100,100,64,64, engineTexture2,'U', 10, 2, 4, "Friendly", 1, 'U', 20, 20, 4, 02);

    //Creates player class to contain list of engines
    private Player player = new Player();


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
        player.addEngine(engine1);
        player.addEngine(engine2);

        // pick a viewport that suits your thing, ExtendViewport is a good start
        viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);

        // ShapeRenderer so we can see our touch point
        shapes = new ShapeRenderer();
        shapeViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(shapeViewport, batch);

        camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        camera.update();

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

        //Render engines
        SpriteBatch batch1 = new SpriteBatch();
        batch1.setProjectionMatrix(camera.combined);
        batch1.begin();
        engine1.render(batch1);
        engine2.render(batch1);
        batch1.end();


        batch.setProjectionMatrix(stage.getCamera().combined);
        if (inputManager.isTouched())
        {
            if ((inputManager.getXCoord() >= engine1.getX() && inputManager.getXCoord() <= engine1.getX() + 64) && (camera.viewportHeight - inputManager.getYCoord() >= engine1.getY() && camera.viewportHeight - inputManager.getYCoord() <= engine1.getY() + 64)){
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

    public int getMapWidth() { return gameMap.getMapWidth(); }
    public int getMapHeight() { return gameMap.getMapHeight(); }
}
