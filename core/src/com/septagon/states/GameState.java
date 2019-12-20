package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.septagon.entites.*;
import com.septagon.game.InputManager;
import com.septagon.game.Player;
import com.septagon.game.UIManager;

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

    private boolean playerTurn = true;

	//Camera that control the viewport of the game depending on input
    private OrthographicCamera camera;
    //Viewport that is used alongside the camera that contains the whole game map
    private ExtendViewport viewport;
    //Spritebatch that is used for rendering all objects in the game
    private SpriteBatch batch;
    private SpriteBatch objectBatch;

    //Contains all the information about our game map
	private TiledGameMap gameMap;
	
    private int timePassed;
    private boolean paused = false;
    private int minigameScore;

    //Loads textures initialises engines
    private Texture engineTexture1 = new Texture(Gdx.files.internal("images/engine1.png"));
    private Texture engineTexture2 = new Texture(Gdx.files.internal("images/engine2.png"));
    private Texture moveSpaceTexture = new Texture(Gdx.files.internal("move_square.png"));
    private Engine engine1;
    private Engine engine2;

    //Loads textures and initialises fortresses
    private ArrayList<Fortress> fortresses;
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

    //These are used to help manage the input of the user when clicking our objects
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Tile currentlyTouchedTile = null;
    private Engine currentEngine = null;

    private UIManager uiManager;


    //Constructor that initialises all necessary variables and also takes in all required values from the game
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
        fortressFire = new Fortress(4, 10, 256, 256, fortressFireTexture, 100, 20, 3);
        fortressMinister = new Fortress(11, 41, 256, 256, fortressMinisterTexture, 100, 20, 3);
        fortressStation = new Fortress(31, 30, 256, 256, fortressStationTexture, 100, 20, 3);
        fireStation = new Station(42, 6, 256, 128, fireStationTexture, 'U');

        fortresses = new ArrayList<Fortress>();
        fortresses.add(fortressFire);
        fortresses.add(fortressMinister);
        fortresses.add(fortressStation);

        entityManager = new EntityManager();
        //Adds all the engines to the player class's list of engines
        player.addEngine(engine1);
        player.addEngine(engine2);

        //Adds all the entities to the entity manager so they can be found
        //more easily.
        entityManager.addEntity(fireStation);
        for(Fortress f: fortresses)
        {
            entityManager.addEntity(f);
        }
        for(Engine e: player.getEngines())
        {
            entityManager.addEntity(e);
        }

        // Intialises the game viewport
        viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);

        //A new sprite batch so we can render the engines and fortresses on top
        //of the tile map.
        objectBatch = new SpriteBatch();
        objectBatch.setProjectionMatrix(camera.combined);
        //Creates instance of uiManager which will be used to render and manage all UI elements
        uiManager = new UIManager(this);

        //Creates and initialises the game map
        gameMap = new TiledGameMap();
        gameMap.initialise();
        entityManager.initialise();
        uiManager.initialise();

        //Moves the camera to its starting position and makes sure the screen gets updated after this
        camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

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

    public void checkIfTouchingFortress(float x, float y)
    {
        int xTilePosition = (int)x / Tile.TILE_SIZE;
        int yTilePosition = (int)y / Tile.TILE_SIZE;

        for(Fortress f: fortresses)
        {
            if(xTilePosition >= f.getX() && xTilePosition <= f.getX() + (f.getWidth() / 32) &&
                    yTilePosition >= f.getY() && yTilePosition <= f.getY() + (f.getHeight() / 32)) {
                f.setSelected(true);
            }
            else {
                f.setSelected(false);
            }
        }
    }


    //Updates the currentlyTouchedTile and if you touch an engine it updates the currentEngine.
    //Calls for the movement of the engine if the touch is in one of the movement grid squares.
    public Boolean touchedTile(float x, float y)
    {
        for(Tile t: tiles) {
            if(t.checkIfClickedInside(x, y)) {
                currentlyTouchedTile = t;
                if (currentEngine != null) {
                    if (currentlyTouchedTile.isMovable() && !currentEngine.isMoved()) {
                        currentEngine.setX(currentlyTouchedTile.getX());
                        currentEngine.setY(currentlyTouchedTile.getY());
                        currentEngine.setMoved(true);
                    }
                }

                checkIfTouchingFortress(x, y);
                for (Engine e: player.getEngines()){
                    if (t.getX() == e.getX() && t.getY() == e.getY()) {
                        currentEngine = e;
                        uiManager.setCurrentEngine(e);
                        setMovableTiles();
                        return true;
                    }
                }
            }
        }
        return false;
    }


    //Creates a grid of tiles that are able to be moved onto,
    // this is achieved by changing the inhabitable attribute of each tile to either;
    // true if it is within moving distance of the current engine, or false if not.
    public void setMovableTiles(){
        //Reset all moveable tiles from previous turn
        for(Tile t: tiles)
        {
            t.setMovable(false);
        }



        for(Tile t: tiles){
            if(t.isInhabitable() && !t.isOccupied()){
                //Creates a grid of movable tiles in a cross shape
                if(t.getX() == currentEngine.getX() && t.getY() == currentEngine.getY()) continue;
                else if((t.getX() <= currentEngine.getX() + currentEngine.getSpeed() && t.getX() >= currentEngine.getX() - currentEngine.getSpeed() && t.getY() == currentEngine.getY())||
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


    //Returns the tile object at a location specified.
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

    	if(allEnginesMoved()){
    	    this.playerTurn = false;
    	    BattleTurn();
        }else{
    	    this.playerTurn = true;
        }
    	//Render the map for our game
    	gameMap.render(camera);
        objectBatch.setProjectionMatrix(camera.combined);
        objectBatch.begin();
        entityManager.render(objectBatch);

        if (inputManager.isHasBeenTouched() && this.playerTurn){
            this.renderMovementGrid(inputManager.getXCoord(), inputManager.getYCoord());
        }

        objectBatch.end();
        uiManager.render();
    }

    //Renders in a grid so that a player can see where they are able to
    //move an engine.
    public void renderMovementGrid(float x, float y){
        if(currentlyTouchedTile != null && currentEngine != null && !currentEngine.isMoved()) {
            //Draw grid around engine with all the movable spaces
            for(Tile t: tiles) {
                if (t.isMovable()) {
                    objectBatch.draw(moveSpaceTexture, t.getX() * Tile.TILE_SIZE, t.getY() * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
            }
        }
    }

    public boolean allEnginesMoved(){
        for(Engine e : player.getEngines()){
            if(!e.isMoved()){
                return false;
            }
        } return true;
    }

    public void BattleTurn(){
        for (Engine e : player.getEngines()){
            e.setMoved(false);
            for (Fortress f: fortresses){
                e.DamageFortressIfInRange(f);
                f.DamageEngineIfInRange(e);
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

    public float getCurrentCameraX()
    {
        return currentCameraX;
    }

    public float getCurrentCameraY()
    {
        return currentCameraY;
    }

    public UIManager getUiManager()
    {
        return uiManager;
    }

    public boolean isPlayerTurn()
    {
        return playerTurn;
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
