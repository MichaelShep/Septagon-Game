package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.septagon.entites.*;
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

    private Texture fortressFireTexture = new Texture(Gdx.files.internal("images/FortressFire.png"));
    private Texture fortressMinisterTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
    private Texture fortressStationTexture = new Texture(Gdx.files.internal("images/FortressStation.png"));
    private Fortress fortressFire;
    private Fortress fortressStation;
    private Fortress fortressMinister;

    private Texture fireStationTexture = new Texture(Gdx.files.internal("images/fireStation.png"));
    private Station fireStation;

    private float currentCameraX, currentCameraY;

    //Creates player class to contain list of engines
    private Player player = new Player();
    private EntityManager entityManager = new EntityManager();

    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Tile currentlyTouchedTile = null;
    private Engine currentEngine = null;


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
        engine1 = new Engine(0,0,32,32, engineTexture1,'U', 10, 2, 4, "Friendly", 2, 'U', 20, 20, 4, 01);
        engine2 = new Engine(40,35,32,32, engineTexture2,'U', 10, 2, 4, "Friendly", 2, 'U', 20, 20, 4, 02);
        fortressFire = new Fortress(4, 10, 256, 256, fortressFireTexture, 100, 20, 20);
        fortressMinister = new Fortress(11, 41, 256, 256, fortressMinisterTexture, 100, 20, 20);
        fortressStation = new Fortress(31, 30, 256, 256, fortressStationTexture, 100, 20, 20);
        fireStation = new Station(42, 6, 256, 128, fireStationTexture, 'U');

        entityManager = new EntityManager();
        //Adds all the engines to the player class's list of engines
        player.addEngine(engine1);
        player.addEngine(engine2);

        entityManager.addEntity(fortressFire);
        entityManager.addEntity(fortressMinister);
        entityManager.addEntity(fortressStation);
        entityManager.addEntity(fireStation);
        for(Engine e: player.getEngines())
        {
            entityManager.addEntity(e);
        }

        // Intialises the game viewport
        viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);

        // ShapeRenderer so we can see our touch point
        shapes = new ShapeRenderer();
        shapeViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeStage = new Stage(shapeViewport, batch);
        shapes.setProjectionMatrix(shapeStage.getCamera().combined);

        objectBatch = new SpriteBatch();

        //Creates and initialises the game map
        gameMap = new TiledGameMap();
        gameMap.initialise();
        entityManager.initialise();

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
        this.setInhabitedTiles();
    }

    //Update all objects in the game
    public void update()
    {
    	gameMap.update();
    	entityManager.update();
    	currentCameraX = camera.position.x;
    	currentCameraY = camera.position.y;
    }

    public Boolean touchedTile(float x, float y)
    {
        for(Tile t: tiles) {
            if(t.checkIfClickedInside(x, y)) {
                currentlyTouchedTile = t;
                if (currentEngine != null) {
                    if (currentlyTouchedTile.isMovable()) {
                        currentEngine.setX(currentlyTouchedTile.getX());
                        currentEngine.setY(currentlyTouchedTile.getY());
                    }
                }
                for (Engine e: player.getEngines()){
                    if (t.getX() == e.getX() && t.getY() == e.getY()) {
                        System.out.println("Have touched a engine");
                        currentEngine = e;
                        setMovableTiles();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setMovableTiles(){
        //Reset all moveable tiles from previous turn
        for(Tile t: tiles)
        {
            t.setMovable(false);
        }

        for(Tile t: tiles){
            if(t.isInhabitable() && !t.isOccupied()){
                if((t.getX() <= currentEngine.getX() + currentEngine.getSpeed() && t.getX() >= currentEngine.getX() - currentEngine.getSpeed() && t.getY() == currentEngine.getY())||
                    (t.getY() <= currentEngine.getY() + currentEngine.getSpeed() && t.getY() >= currentEngine.getY() - currentEngine.getSpeed() && t.getX() == currentEngine.getX())){
                        t.setMovable(true);
                }
            }else {
                    t.setMovable(false);
                }
        }
    }

    //Sets up all the tiles that are currently occupied by fortress or the fire station
    public void setInhabitedTiles()
    {
        //Set the all the tiles within the fire station fortress bounds as occupied
        for(int x = 4; x < 12; x++)
        {
            for(int y = 10; y < 15; y++)
            {
                Tile t = getTileAtLocation(x, y);
                if(t != null)
                    t.setOccupied(true);
            }
        }

        //Sets all the tiles within the minister fortress as occupied
        for(int x = 11; x < 19; x++)
        {
            for(int y = 41; y < 48; y++)
            {
                Tile t = getTileAtLocation(x, y);
                if(t != null)
                    t.setOccupied(true);
            }
        }

        //Sets all the tiles within the station fortress as occupied
        for(int x = 31; x < 39; x++)
        {
            for(int y = 30; y < 34; y++)
            {
                Tile t = getTileAtLocation(x, y);
                if(t != null)
                    t.setOccupied(true);
            }
        }

        //Sets all the tiles in the fire station as occupied
        for(int x = 42; x < 50; x++)
        {
            for(int y = 6; y < 10; y++)
            {
                Tile t = getTileAtLocation(x, y);
                if(t != null)
                    t.setOccupied(true);
            }
        }
    }

    private Tile getTileAtLocation(int x, int y)
    {
        for(Tile t: tiles)
        {
            if(t.getX() == x && t.getY() == y)
                return t;
        }
        return null;
    }

    public void render(SpriteBatch batch)
    {
    	//Clear the background to red
    	Gdx.gl.glClearColor(1, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    	//Render the map for our game
    	gameMap.render(camera);

        //Render engines
        //SpriteBatch batch1 = new SpriteBatch();
        //batch1.setProjectionMatrix(camera.combined);
        //batch1.begin();
        //engine1.render(batch);
        //engine2.render(batch1);
        //batch1.end();
        objectBatch.setProjectionMatrix(camera.combined);
        objectBatch.begin();

        entityManager.render(objectBatch);
        objectBatch.end();

        if (inputManager.isHasBeenTouched()){
            this.renderMovementGrid(inputManager.getXCoord(), inputManager.getYCoord());

        }

    }

    public void renderMovementGrid(float x, float y){
        if(currentlyTouchedTile != null && currentEngine != null) {
            shapes.begin(ShapeRenderer.ShapeType.Line);
            shapes.setColor(0, 0, 1, 1);

            //Draw grid around engine with all the movable spaces
            for(Tile t: tiles) {
                if (t.isMovable()) {
                    shapes.rect(t.getX() * Tile.TILE_SIZE, t.getY() * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
            }
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
