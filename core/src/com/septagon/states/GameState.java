package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.septagon.entites.*;
import com.septagon.game.InputManager;
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

    //Variable to keep track of whether it is the player or enemies turn
    private boolean playerTurn = true;

	//Camera that control the viewport of the game depending on input
    private OrthographicCamera camera;
    //Viewport that is used alongside the camera that contains the whole game map
    private ExtendViewport viewport;
    //Spritebatch that is used for rendering all objects in the game
    private SpriteBatch objectBatch;

    //Contains all the information about our game map
	private TiledGameMap gameMap;
	
    private int timePassed;
    private boolean paused = false;
    private int minigameScore;

    //Loads textures and creates objects for the engines
    private ArrayList<Engine> engines;
    private Texture engineTexture1 = new Texture(Gdx.files.internal("images/engine1.png"));
    private Texture engineTexture2 = new Texture(Gdx.files.internal("images/engine2.png"));
    private Texture moveSpaceTexture = new Texture(Gdx.files.internal("move_square.png"));
    private Engine engine1;
    private Engine engine2;

    //Loads textures and creates objects for the fortresses
    private ArrayList<Fortress> fortresses;
    private Texture fortressFireTexture = new Texture(Gdx.files.internal("images/FortressFire.png"));
    private Texture fortressMinisterTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
    private Texture fortressStationTexture = new Texture(Gdx.files.internal("images/FortressStation.png"));
    private Fortress fortressFire;
    private Fortress fortressStation;
    private Fortress fortressMinister;

    //Loads textures and creates an object for the fire station
    private Texture fireStationTexture = new Texture(Gdx.files.internal("images/fireStation.png"));
    private Station fireStation;

    //Keeps track of where in the game map the camera is currently
    private float currentCameraX, currentCameraY;

    //Create entityManager that will handle all entities in our game
    private EntityManager entityManager = new EntityManager();

    //These are used to help manage the input of the user when clicking our objects
    private ArrayList<Tile> tiles = new ArrayList<Tile>();
    private Tile currentlyTouchedTile = null;
    private Tile previouslyTouchedTile = null;
    private Engine currentEngine = null;

    //Creates instance of class that controls all the ui elements that stay on the screen
    private UIManager uiManager;

    //Creates an array of bullets
    ArrayList<Bullet> bullets;
    private boolean shouldCreateBullets = false;

    /***
     * Constructor that sets inital values for all variables and gets values of variables that are used throughout full program
     * @param inputManager The games input manager that handles all the games input
     * @param font The font being used for the game
     * @param camera The camera that controls what is displayed on the screen
     */
    public GameState(InputManager inputManager, BitmapFont font, OrthographicCamera camera)
    {
        super(inputManager, font, StateID.GAME);
    	this.camera = camera;
        timePassed = 0;
        minigameScore = 0;
        currentCameraX = 0;
        currentCameraY = 0;
        
        bullets = new ArrayList<Bullet>();
    }

    /***
     * Sets up all objects in our game and gets the game ready to be played
     */
    public void initialise()
    {
        //Initialises all engines, fortress and stations in the game
        engine1 = new Engine(0,0, engineTexture1, 10, 2, 4, 2, 20, 4, 01);
        engine2 = new Engine(0,10, engineTexture2, 10, 2, 4, 2, 20, 4, 02);
        fortressFire = new Fortress(4, 10, 256, 256, fortressFireTexture, 100, 20, 3);
        fortressMinister = new Fortress(11, 41, 256, 256, fortressMinisterTexture, 100, 20, 3);
        fortressStation = new Fortress(31, 30, 256, 256, fortressStationTexture, 100, 20, 3);
        fireStation = new Station(42, 6, 256, 128, fireStationTexture);

        //Adds all the fortresses to the ArrayList of fortresses
        fortresses = new ArrayList<Fortress>();
        fortresses.add(fortressFire);
        fortresses.add(fortressMinister);
        fortresses.add(fortressStation);

        //Sets the engines positions so that they start from the fireStation
        engine1.setCol(fireStation.getCol() + 3);
        engine1.setRow(fireStation.getRow() - 1);
        engine2.setCol(fireStation.getCol() + 5);
        engine2.setRow(fireStation.getRow() - 1);

        //Adds all the engines to the ArrayList of engines
        engines = new ArrayList<Engine>();
        engines.add(engine1);
        engines.add(engine2);

        //Adds all the entities to the entity manager so all their updating and rendering can be handled
        entityManager = new EntityManager();
        entityManager.addEntity(fireStation);
        for(Fortress f: fortresses)
        {
            entityManager.addEntity(f);
        }
        for(Engine e: engines)
        {
            entityManager.addEntity(e);
        }

        // Intialises the game viewport and spritebatch
        viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
        objectBatch = new SpriteBatch();
        objectBatch.setProjectionMatrix(camera.combined);

        //Creates instance of uiManager which will be used to render and manage all UI elements
        uiManager = new UIManager(this);

        //Creates the gameMap instance that will be used to load the map from the tmx file
        gameMap = new TiledGameMap();

        //Intialises all entities and all ui elements
        entityManager.initialise();
        uiManager.initialise();

        //Sets up the camera parameters and moves it to its inital position
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.x = Gdx.graphics.getWidth() / 2 + (getMapWidth() * Tile.TILE_SIZE) - Gdx.graphics.getWidth();
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

        //Sets up all the occupied tiles on the map so they cannot be moved to
        this.setOccupiedTiles();
    }

    /***
     * Update method that is called every frame and will update and move all objects if required
     */
    public void update()
    {
        if(shouldCreateBullets)
        {
            this.createBullets();
        }

        //Call the update method for all entities in our game
    	entityManager.update();

        //Updates the pointers to the current x and y positions of the camera
    	currentCameraX = camera.position.x;
    	currentCameraY = camera.position.y;
    }

    /***
     * Check if the user has pressed on a fortress and display a bounding box if they have
     * @param x The x position of the input - in world coordinates
     * @param y The y position of the input - in world coordinates
     */
    public void checkIfTouchingFortress(float x, float y)
    {
        //Converts positions of input into tile column and row positions (map locations)
        int tileCol = (int)x / Tile.TILE_SIZE;
        int tileRow = (int)y / Tile.TILE_SIZE;

        //Loops through all fortresses to check if any have been pressed
        for(Fortress f: fortresses)
        {
            //If the clicked on tile is within the bounds of the fortress make it selected, if not make not selected
            if(tileCol >= f.getX() && tileCol <= f.getX() + (f.getWidth() / 32) &&
                   tileRow >= f.getY() && tileRow <= f.getY() + (f.getHeight() / 32)) {
                f.setSelected(true);
            }
            else {
                f.setSelected(false);
            }
        }
    }


    /***
     * Called when the InputManager detects an input and is used to work out what tile was pressed and what should occur as a result
     * @param x X position of the input
     * @param y Y position of the input
     * @return boolean that will say if a tile has been pressed or not (true if it has been pressed)
     */
    public Boolean touchedTile(float x, float y)
    {
        //Loops through all tiles to see if it has been pressed
        for(Tile t: tiles) {
            //WANT TO ADD SOME EFFICIENCY CODE HERE THAT FIRSTLY CHECKS IF TILE ON SCREEN BEFORE PROCESSING
            //When we have found the tile that has been pressed, perform neccessary processing
            if(t.checkIfClickedInside(x, y)) {
                //updated the pointers to the current and previous tiles
                previouslyTouchedTile = currentlyTouchedTile;
                currentlyTouchedTile = t;
                //if an engine has been previously pressed on, check on if a valid move has been pressed
                //and if so perform that move
                if (currentEngine != null) {
                    if (currentlyTouchedTile.isMovable() && !currentEngine.isMoved()) {
                        currentlyTouchedTile.setOccupied(true);
                        previouslyTouchedTile.setOccupied(false);
                        currentEngine.setX(currentlyTouchedTile.getX());
                        currentEngine.setY(currentlyTouchedTile.getY());
                        currentEngine.setMoved(true);
                        break;
                    }
                }

                //If not a moveable tile pressed, check if a fortress tile has been pressed
                checkIfTouchingFortress(x, y);
                for (Engine e: engines){
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
            if(!t.isOccupied()){
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
    public void setOccupiedTiles()
    {
        //Set the tiles that currently have an engine on to be occupied
        for(Engine e: engines)
        {
            for(Tile t: tiles)
            {
                if(t.getX() == e.getX() && t.getY() == e.getY())
                {
                    t.setOccupied(true);
                    break;
                }
            }
        }

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

    private void createBullets()
    {
        // shooting bullet
        bullets.add(new Bullet(40));
        bullets.add(new Bullet(44));
        System.out.println("Bullet has been created");
        shouldCreateBullets = false;
    }

    public void render(SpriteBatch batch)
    {
    	// update bullet
    	ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();
    	for (Bullet bullet : bullets) {
    		bullet.update(currentCameraX);
    		if (bullet.remove)
    			bulletToRemove.add(bullet);
    	}
    	bullets.removeAll(bulletToRemove);
    	
    	
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
        for (Bullet bullet : bullets) {
        	bullet.render(objectBatch);
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
        for(Engine e : engines){
            if(!e.isMoved()){
                return false;
            }
        } return true;
    }

    public void BattleTurn(){
        for (Engine e : engines){
            e.setMoved(false);
            for (Fortress f: fortresses){
                e.DamageFortressIfInRange(f);
                f.DamageEngineIfInRange(e);
            }

        }
    }

    //Method that is called when the screen is resized - makes sure camera stays in map bounds
    public void hasResized()
    {
        if(camera.position.x <= (Gdx.graphics.getWidth() / 2)) {
            camera.position.x = Gdx.graphics.getWidth() / 2;
        }
        if(camera.position.y <= (Gdx.graphics.getHeight() / 2)) {
            camera.position.y = Gdx.graphics.getHeight() / 2;
        }

        if(camera.position.x >= getMapWidth() * Tile.TILE_SIZE - Gdx.graphics.getWidth() / 2){
            camera.position.x = getMapWidth() * Tile.TILE_SIZE - Gdx.graphics.getWidth() / 2;
        }
        if(camera.position.y >= getMapHeight() * Tile.TILE_SIZE - Gdx.graphics.getHeight() / 2){
            camera.position.x = getMapHeight() * Tile.TILE_SIZE - Gdx.graphics.getHeight() / 2;
        }
        //uiManager.setupPositions();
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

    public boolean isShouldCreateBullets() { return shouldCreateBullets; }

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

    public void setShouldCreateBullets(boolean shouldCreateBullets) { this.shouldCreateBullets = shouldCreateBullets; }

    public int getMapWidth() { return gameMap.getMapWidth(); }
    public int getMapHeight() { return gameMap.getMapHeight(); }
}
