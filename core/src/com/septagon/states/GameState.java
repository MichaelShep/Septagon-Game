package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.septagon.entites.*;
import com.septagon.game.InputManager;
import com.septagon.game.UIManager;
import com.septagon.helperClasses.AssetManager;
import com.septagon.helperClasses.StatusBarGenerator;
import com.septagon.helperClasses.TileManager;

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
    public final static float VP_WIDTH = 640;
    public final static float VP_HEIGHT = 480;

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
    private Engine engine1;
    private Engine engine2;

    //Loads textures and creates objects for the fortresses
    private ArrayList<Fortress> fortresses;
    private Fortress fortressFire;
    private Fortress fortressStation;
    private Fortress fortressMinister;

    //Loads textures and creates an object for the fire station
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
    public static ArrayList<Bullet> bullets;
    //private boolean shouldCreateBullets = false;

    private StatusBarGenerator statusBarGenerator;
    private TileManager tileManager;

    private int currentFortressIndex = 0;
    private int counter = 0;
    private boolean hasChangedFortress = false;

    private int changeTurnCounter = 0;
    private boolean changingTurn = false;

    /***
     * Constructor that sets inital values for all variables and gets values of variables that are used throughout full program
     * @param inputManager The games input manager that handles all the games input
     * @param font The font being used for the game
     * @param camera The camera that controls what is displayed on the screen
     */
    public GameState(InputManager inputManager, BitmapFont font, StateManager stateManager, OrthographicCamera camera)
    {
        super(inputManager, font, StateID.GAME, stateManager);
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
        engine1 = new Engine(0,0, AssetManager.getEngineTexture1(), 100, 15, 4, 3, 60, 4, 01);
        engine2 = new Engine(0,0, AssetManager.getEngineTexture2(), 100, 10, 4, 4, 40, 4, 02);
        fortressFire = new Fortress(4, 10, 256, 256, AssetManager.getFortressFireTexture(), AssetManager.getDefeatedFireTexture(), 100, 20, 3);
        fortressMinister = new Fortress(11, 41, 256, 256, AssetManager.getFortressMinisterTexture(), AssetManager.getDefeatedMinsterTexture(), 100, 20, 3);
        fortressStation = new Fortress(31, 30, 256, 256, AssetManager.getFortressStationTexture(), AssetManager.getDefeatedStationTexture(), 100, 20, 3);
        fireStation = new Station(42, 6, 256, 128, AssetManager.getFireStationTexture());

        //Adds all the fortresses to the ArrayList of fortresses
        fortresses = new ArrayList<Fortress>();
        fortresses.add(fortressFire);
        fortresses.add(fortressMinister);
        fortresses.add(fortressStation);

        //Sets the engines positions so that they start from the fireStation
        engine1.setCol(fireStation.getCol() + 5 - 40);
        engine1.setRow(fireStation.getRow() - 1);
        engine2.setCol(fireStation.getCol() + 3 - 40);
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
        uiManager = new UIManager(this, font);

        //Creates the gameMap instance that will be used to load the map from the tmx file
        gameMap = new TiledGameMap();

        //Intialises all entities and all ui elements
        entityManager.initialise();
        uiManager.initialise();

        //Sets up the camera parameters and moves it to its inital position
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        //Create objects referring to all tiles in game
        for(int row = 0; row < gameMap.getMapHeight(); row++)
        {
            for(int col = 0; col < gameMap.getMapWidth(); col++)
            {
                if(gameMap.getTileByCoordinate(0, col, row) != null)
                    tiles.add(gameMap.getTileByCoordinate(0, col, row));
            }
        }

        //Initialises the statusBarRenderer object
        statusBarGenerator = new StatusBarGenerator(engines, fortresses);

        //Sets up all the occupied tiles on the map so they cannot be moved to
        tileManager = new TileManager(engines, tiles);
        tileManager.setOccupiedTiles(gameMap);
    }

    /***
     * Update method that is called every frame and will update and move all objects if required
     */
    public void update()
    {
        /*        if(shouldCreateBullets)
        {
            this.createBullets(44,44);
        }

         */
        this.paused = uiManager.isPaused();
        //Update the bullets
        ArrayList<Bullet> bulletToRemove = new ArrayList<Bullet>();
        for (Bullet bullet : bullets)
        {
            float deltaTime = 1 / 60f;
            bullet.update(deltaTime);
            if (bullet.remove)
                bulletToRemove.add(bullet);
        }
        bullets.removeAll(bulletToRemove);

        if(changingTurn){
            changeTurnCounter++;
            if(changeTurnCounter >= 30){
                changeTurnCounter = 0;
                playerTurn = !playerTurn;
                changingTurn = false;
            }
        }
        else if(!paused && playerTurn)
        {
            //Call the update method for all entities in our game
            entityManager.update();

            //If all the engines have been moved on the current turn, make it the enemies turn
            if (allEnginesMoved())
            {
                this.changingTurn = true;
                changeTurnCounter = 0;
            }

            //Updates the pointers to the current x and y positions of the camera
            currentCameraX = camera.position.x;
            currentCameraY = camera.position.y;

            //Checks if the player has destroyed all the fortresses
            boolean hasWon = true;
            for (Fortress f : fortresses)
            {
                if (f.getHealth() > 0) hasWon = false;
            }
            if (hasWon)
            {
                stateManager.changeState(new GameOverState(inputManager, font, stateManager, true));
            }

            //Checks if all the players fire engines have been destroyed
            boolean hasLost = true;
            hasLost = checkIfAllEnginesDead();
            if (hasLost)
            {
                stateManager.changeState(new GameOverState(inputManager, font, stateManager, false));
            }
        }else if(!paused){
            boolean shouldShowFortress = false;
            if(!hasChangedFortress){
                if(currentFortressIndex >= fortresses.size()){
                    currentFortressIndex = 0;
                    if(checkIfAllEnginesDead()){
                        stateManager.changeState(new GameOverState(inputManager, font, stateManager, false));
                        return;
                    }
                    this.snapToAttacker(engines.get(0));
                    tileManager.resetMovableTiles();
                    for(Engine e: engines){
                        e.setMoved(false);
                        e.ifInRangeFill(fireStation);
                    }
                    playerTurn = true;
                    return;
                }
                Fortress nextFortress = fortresses.get(currentFortressIndex);
                for(Engine e: engines){
                    int xPosition = e.getX() + (e.getWidth() / 2) - (Gdx.graphics.getWidth() / 2);
                    int yPosition = e.getY() + (e.getHeight() / 2) - (Gdx.graphics.getHeight() / 2);
                    if(nextFortress.getX() >= xPosition && nextFortress.getX() <= xPosition + Gdx.graphics.getWidth() &&
                    nextFortress.getY() >= yPosition && nextFortress.getY() <= yPosition + Gdx.graphics.getHeight()){
                        shouldShowFortress = true;
                    }
                    else if(nextFortress.getX() + nextFortress.getWidth() >= xPosition && nextFortress.getX() +
                    nextFortress.getWidth() <= xPosition + Gdx.graphics.getWidth() && nextFortress.getY() +
                    nextFortress.getHeight() >= yPosition && nextFortress.getY() <= yPosition + Gdx.graphics.getHeight()){
                        shouldShowFortress = true;
                    }
                }
                if(shouldShowFortress)
                {
                    this.snapToAttacker(nextFortress);
                    BattleTurn(nextFortress);
                }
                else{
                    currentFortressIndex++;
                    hasChangedFortress = false;
                }
                hasChangedFortress = true;
            }else
            {
                counter++;
                if(counter >= 180){
                    hasChangedFortress = false;
                    currentFortressIndex++;
                    counter = 0;
                }
            }
        }
    }

    private boolean checkIfAllEnginesDead(){
        for(Engine e: engines){
            if(!e.isDead()) return false;
        }
        return true;
    }

    private void snapToAttacker(Attacker a){
        int newCameraX = a.getX() + (a.getWidth() / 2);
        int newCameraY = a.getY() + (a.getHeight() / 2);

        if(newCameraX <= Gdx.graphics.getWidth() / 2)
            newCameraX = Gdx.graphics.getWidth() / 2;
        else if(newCameraX >= (gameMap.getMapWidth() * Tile.TILE_SIZE) - Gdx.graphics.getWidth() / 2)
            newCameraX = (gameMap.getMapWidth() * Tile.TILE_SIZE) - Gdx.graphics.getWidth() / 2;

        if(newCameraY <= Gdx.graphics.getHeight() / 2)
            newCameraY = Gdx.graphics.getHeight() / 2;
        else if(newCameraY >= (gameMap.getMapHeight() * Tile.TILE_SIZE) - Gdx.graphics.getHeight() / 2)
            newCameraY = (gameMap.getMapHeight() * Tile.TILE_SIZE) - Gdx.graphics.getHeight() / 2;

        camera.position.x = newCameraX;
        camera.position.y = newCameraY;
        camera.update();
    }

    /***
     * Check if the user has pressed on a fortress and display a bounding box if they have
     * @param x The x position of the input - in world coordinates
     * @param y The y position of the input - in world coordinates
     */
    public void checkIfTouchingFortress(float x, float y)
    {
        //Loops through all fortresses to check if any have been pressed
        for(Fortress f: fortresses)
        {
            //If the clicked on tile is within the bounds of the fortress make it selected, if not make not selected
            if(x >= f.getX() && x <= f.getX() + f.getWidth() &&
                    y >= f.getY() && y <= f.getY() + f.getHeight()) {
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
                    if (currentlyTouchedTile.isMovable() && !currentEngine.isMoved() && !currentEngine.isDead()) {
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
                    if (t.getCol() == e.getCol() && t.getRow() == e.getRow()) {
                        currentEngine = e;
                        uiManager.setCurrentEngine(e);
                        tileManager.setMovableTiles(currentEngine);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /***
     * Method that will render everything in the game each frame
     * @param batch The batch which is used for all the rendering
     */
    public void render(SpriteBatch batch)
    {
        //Clear the background to red - the colour does not reall matter
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render the map and all objects for our game
        gameMap.render(camera);
        objectBatch.setProjectionMatrix(camera.combined);
        objectBatch.begin();
        entityManager.render(objectBatch);
        for (Bullet bullet : bullets) {
            bullet.render(objectBatch);
        }

        //Renderers the movement grid for the currently touched engine
        if (inputManager.isHasBeenTouched() && this.playerTurn){
            this.renderMovementGrid();
        }

        //Ends the drawing of all the objects for the current frame
        objectBatch.end();
        statusBarGenerator.renderBars(camera);


        //renders all the ui elements
        uiManager.render();
    }

    /***
     * Renders a grid showing the player where the engine that they have pressed on can move to
     */
    public void renderMovementGrid(){
        //If there is a engine that has been pressed and that engine has not yet moved this turn
        if(currentlyTouchedTile != null && currentEngine != null && !currentEngine.isMoved() && !currentEngine.isDead()) {
            //Draw grid around engine with all the movable spaces
            for(Tile t: tiles) {
                if (t.isMovable()) {
                    objectBatch.draw(AssetManager.getMoveSpaceTexture(), t.getX(), t.getY(), Tile.TILE_SIZE, Tile.TILE_SIZE);
                }
            }
        }
    }

    /***
     * Checks if all engines have been moved or not so that the game knows when to end the players turn
     * @return boolean of whether all the engines have been moved or not
     */
    public boolean allEnginesMoved(){
        for(Engine e : engines){
            if(!e.isMoved()){
                return false;
            }
        }
        return true;
    }

    /***
     * Method that is run for the phase of the game where damage events occur (damage, filling etc) turn
     */
    public void BattleTurn(Fortress f){
        //Set the moved variable to false for each engine and then check if damages can occur
        tileManager.resetMovableTiles();
        for (int i = 0; i < engines.size(); i++){
            engines.get(i).setMoved(false);
            engines.get(i).DamageFortressIfInRange(f);
            f.DamageEngineIfInRange(engines.get(i));
            if (engines.get(i).isDead()){
                engines.remove(engines.get(i));
                break;
            }
            if (f.isDead()){
                fortresses.remove(f);
            }
            engines.get(i).ifInRangeFill(fireStation);
        }
    }

    /***
     * Method that handles map resizing when the window size is changed
     */
    public void hasResized()
    {
        //Checks that the change in screen size has not caused the camera to show features off the map
        //If this is the case, clamp the camera position so that it sets it back to the edge of the map
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

    //public boolean isShouldCreateBullets() { return shouldCreateBullets; }

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
        uiManager.setPaused(paused);
    }

    public void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }

    //public void setShouldCreateBullets(boolean shouldCreateBullets) { this.shouldCreateBullets = shouldCreateBullets; }

    public int getMapWidth() { return gameMap.getMapWidth(); }
    public int getMapHeight() { return gameMap.getMapHeight(); }
}