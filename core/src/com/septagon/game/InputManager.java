package com.septagon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.septagon.entites.Tile;
import com.septagon.states.*;


/*
Class used to handle all inputs from the user
 */
public class InputManager implements InputProcessor
{
    Vector3 tp = new Vector3();
    private boolean dragging;
    private OrthographicCamera camera;
    private StateManager stateManager;

    private BitmapFont font;
    private SpriteBatch batch;

    //Variables to keep track of when and where a touch occured on the screen
    private boolean hasBeenTouched = false;
    private float xCoord;
    private float yCoord;

    /**
     * Constructor to pass all values of neccessary variables to the InputManager
     */
    public InputManager(OrthographicCamera camera, StateManager stateManager, BitmapFont font, SpriteBatch batch)
    {
        this.camera = camera;
        this.stateManager = stateManager;
        this.font = font;
        this.batch = batch;
    }

    //Unused method that is required since we implementing InputProcessor
    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    /***
     * Method that will be called when the user presses with their mouse on the screen
     * @param screenX The x position of the input
     * @param screenY The y position of the input
     * @param pointer
     * @param button The button on the mouse that was used to perform the input
     * @return
     */
    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        //checks if the currentState is a GameState, and if so performs neccessary operations
        if(stateManager.getCurrentState().getID() == State.StateID.GAME)
        {
            //Cast the currentState to a GameState so that GameState specific methods can be used
            GameState currentState = (GameState)stateManager.getCurrentState();

            if(!currentState.isPaused() && currentState.isPlayerTurn())
            {
                // ignore if its not left mouse button or first touch pointer
                if (button != Input.Buttons.LEFT || pointer > 0) return false;

                //Get the details of the input
                hasBeenTouched = true;
                xCoord = Gdx.input.getX();
                yCoord = Gdx.input.getY();

                //Convert input coords to world coordinates
                xCoord = xCoord + camera.position.x - (Gdx.graphics.getWidth() / 2);
                yCoord = (Gdx.graphics.getHeight() - yCoord) + camera.position.y - (Gdx.graphics.getHeight() / 2);

                //Create version of input variables where they are still in term of screen coords
                float onScreenXCoord = Gdx.input.getX();
                float onScreenYCoord = Gdx.graphics.getHeight() - Gdx.input.getY();

                //Check if the user has pressed any of the UI elements on the screen
                if (currentState.getUiManager().getShowStatsRect().contains(onScreenXCoord, onScreenYCoord))
                {
                    currentState.getUiManager().pressedShowStatsButton();
                }
                if (currentState.getUiManager().getMinimiseRect().contains(onScreenXCoord, onScreenYCoord))
                {
                    currentState.getUiManager().pressedMinimiseButton();
                }

                //Call gameState method that handles a press on the gameMap with the x and y positions
                currentState.touchedTile(xCoord, yCoord);
                dragging = true;
            }
        }
        return true;
    }

    /**
     * Check whether the user has performed a dragging action on the screen
     * @param screenX The x position of the input
     * @param screenY The y position of the input
     * @param pointer
     * @return
     */
    @Override public boolean touchDragged (int screenX, int screenY, int pointer)
    {
        //Check if the currentState is the gameState
        if(stateManager.getCurrentState().getID() == State.StateID.GAME)
        {
            //Cast the currentState to a GameState so GameState specific methods can be used
            GameState currentState = (GameState) stateManager.getCurrentState();
            if(!currentState.isPaused() && currentState.isPlayerTurn())
            {
                if (!dragging) return false;

                //Get the position of where the camera should move to on the map
                float newX = camera.position.x - Gdx.input.getDeltaX();
                float newY = camera.position.y + Gdx.input.getDeltaY();

                //Check that the movement is within the bounds of the map before moving the camera
                if (newX >= Gdx.graphics.getWidth() / 2 && newX <= currentState.getMapWidth() * Tile.TILE_SIZE - Gdx.graphics.getWidth() / 2)
                    camera.translate(-Gdx.input.getDeltaX(), 0, 0);

                if (newY >= Gdx.graphics.getHeight() / 2 && newY <= currentState.getMapHeight() * Tile.TILE_SIZE - Gdx.graphics.getHeight() / 2)
                    camera.translate(0, Gdx.input.getDeltaY(), 0);

                //Update position of the camera
                camera.update();
                camera.unproject(tp.set(screenX, screenY, 0));
            }
        }
        return true;
    }

    /***
     * Checks if the user has let go of an input button
     * @param screenX The x position of the input
     * @param screenY The y position of the input
     * @param pointer
     * @param button Which button the input was performed with
     * @return
     */
    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        //Checks if the currentState is a GameState
        if(stateManager.getCurrentState().getID() == State.StateID.GAME)
        {
            if (button != Input.Buttons.LEFT || pointer > 0) return false;
            camera.unproject(tp.set(screenX, screenY, 0));
            dragging = false;
        }
        return true;
    }

    /***
     * Method that checks whether the user has pressed a key on the keyboard
     * @param keycode The code of which key was pressed
     * @return
     */
    @Override public boolean keyDown (int keycode)
    {
        //Checks if the currentState is a menuState
        if(stateManager.getCurrentState().getID() == State.StateID.MENU)
        {
            //Casts the currentState to a menuState so MenuState specific operations can be performed
            MenuState currentState = (MenuState) stateManager.getCurrentState();

            //Check whether the user is moving around in the menu
            if(keycode == Input.Keys.DOWN)
            {
                currentState.setMenuPosition(currentState.getMenuPosition() + 1);
            }else if(keycode == Input.Keys.UP)
            {
                currentState.setMenuPosition(currentState.getMenuPosition() - 1);
            }

            //Checks whether the user has pressed enter, if so will perform action based on where user is in menu
            else if(keycode == Input.Keys.ENTER)
            {
                switch(currentState.getMenuPosition())
                {
                    case 0:
                        stateManager.changeState(new GameState(this, font, stateManager, camera));
                        break;
                    case 1:
                        Gdx.app.exit();
                        break;
                    default:
                        System.err.println("Something went wrong with the menu system");
                        break;
                }
            }
        }
        //If the user is in the gameState
        else if(stateManager.getCurrentState().getID() == State.StateID.GAME){
            //Cast the currentState to a GameState so gamestate specific methods can be used
            GameState currentState = (GameState) stateManager.getCurrentState();

            //If the user presses escape, invert whether the game is paused or not
            if(keycode == Input.Keys.ESCAPE){
                currentState.setPaused(!currentState.isPaused());
            }
            //If the user is paused, check wether they are moving through the menu or pressing on an option
            if(currentState.isPaused()){
                if(keycode == Input.Keys.DOWN && currentState.getUiManager().getPausePosition() == 1){
                    currentState.getUiManager().setPausePosition(2);
                }else if(keycode == Input.Keys.UP && currentState.getUiManager().getPausePosition() == 2){
                    currentState.getUiManager().setPausePosition(1);
                }

                if(keycode == Input.Keys.ENTER){
                    if(currentState.getUiManager().getPausePosition() == 1){
                        currentState.setPaused(false);
                    }else{
                        stateManager.changeState(new MenuState(this, font, stateManager));
                    }
                }
            }
        }

        //Handle input for the game over state
        else if(stateManager.getCurrentState().getID() == State.StateID.GAME_OVER){
            //Convert the currentState variable to an instance of GameOverState
            GameOverState currentState = (GameOverState) stateManager.getCurrentState();

            //Move the position of the gameOverState up or down based on inputs
            if(keycode == Input.Keys.DOWN && currentState.getPosition() == 1){
                currentState.setPosition(2);
            }else if(keycode == Input.Keys.UP && currentState.getPosition() == 2){
                currentState.setPosition(1);
            }
            //If the enter key is pressed, perform action based on the position
            if(keycode == Input.Keys.ENTER){
                //If on yes, start a new GameState
                if(currentState.getPosition() == 1){
                    stateManager.changeState(new GameState(this, font, stateManager, camera));
                }
                //If on no, close the window and exit the game
                else if(currentState.getPosition() == 2){
                    Gdx.app.exit();
                }
            }
        }
        return true;
    }

    //Unused methods that are required since we are overriding InputManager
    @Override public boolean keyUp (int keycode) {
        return false;
    }

    @Override public boolean keyTyped (char character) {
        return false;
    }

    @Override public boolean scrolled (int amount) {
        return false;
    }

    //Getters
    public boolean isHasBeenTouched() { return hasBeenTouched; }
    public float getXCoord() { return xCoord; }
    public float getYCoord() { return yCoord; }
}