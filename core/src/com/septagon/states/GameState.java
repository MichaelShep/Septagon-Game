package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.septagon.entites.Tile;
import com.septagon.entites.TileType;
import com.septagon.entites.TiledGameMap;

/*
Child class of the State class that will manage the system when the user is in the game
 */

public class GameState extends State
{
	//Camera that control the viewport of the game depending on input
	private OrthographicCamera camera;
	
	private TiledGameMap gameMap;
	
    private int timePassed;
    private boolean paused = false;
    private int minigameScore;

    public GameState(OrthographicCamera camera)
    {
    	this.camera = camera;
        timePassed = 0;
        minigameScore = 0;
    }

    public void initialise()
    {
        gameMap = new TiledGameMap();
    	//Initialises the tileType hash map
    	TileType.setupTileTypeMap();
    	gameMap.initialise();
    }

    public void update()
    {
    	//Test code to output the tile that has been clicked on
    	if(Gdx.input.justTouched())
    	{
    		//Convert the coordinates of the mouse press on the screen to the mouse press in the
    		//game world/map
    		Vector3 position = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    		Tile tile = gameMap.getTileByLocation(1, (int)position.x, (int)position.y);
    		if(tile != null)
    		{
    			//Prints out the type of tile that the user pressed on
    			TileType type = tile.getType();
    			System.out.println("You clicked on a " + type.getName() + " tile");
    		}
    	}
    	
    	gameMap.update();
    }

    public void render(SpriteBatch batch)
    {
    	//Clear the background to red
    	Gdx.gl.glClearColor(1, 0, 0, 1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	
    	
    	//Bit of test input code to allow us to move the camera around
    	if(Gdx.input.isTouched())
    	{
    		camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
    		camera.update();
    	}
    	
    	//Render the map for our game
    	gameMap.render(batch);
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
