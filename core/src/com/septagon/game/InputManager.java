package com.septagon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.septagon.states.*;

public class InputManager implements InputProcessor
{
    Vector3 tp = new Vector3();
    private boolean dragging;
    private OrthographicCamera camera;
    private StateManager stateManager;

    private BitmapFont font;
    private SpriteBatch batch;

    private Boolean touched = false;
    private int xCoord;
    private int yCoord;

    public InputManager(OrthographicCamera camera, StateManager stateManager, BitmapFont font, SpriteBatch batch)
    {
        this.camera = camera;
        this.stateManager = stateManager;
        this.font = font;
        this.batch = batch;
    }

    @Override public boolean mouseMoved (int screenX, int screenY) {
        // we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if(stateManager.getCurrentState().getID() == State.StateID.GAME)
        {
            // ignore if its not left mouse button or first touch pointer
            if (button != Input.Buttons.LEFT || pointer > 0) return false;
            //camera.unproject(tp.set(screenX, screenY, 0));
            touched = !touched;
            xCoord = Gdx.input.getX();
            yCoord = Gdx.input.getY();
            dragging = true;
        }
        return true;
    }

    @Override public boolean touchDragged (int screenX, int screenY, int pointer)
    {
        if(stateManager.getCurrentState().getID() == State.StateID.GAME)
        {
            if (!dragging) return false;
            touched = false;
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY(), 0);
            camera.update();
            camera.unproject(tp.set(screenX, screenY, 0));
        }
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if(stateManager.getCurrentState().getID() == State.StateID.GAME)
        {
            if (button != Input.Buttons.LEFT || pointer > 0) return false;
            camera.unproject(tp.set(screenX, screenY, 0));
            dragging = false;
        }
        return true;
    }

	/*@Override public void resize (int width, int height) {
		// viewport must be updated for it to work properly
		viewport.update(width, height, true);
	}*/


    @Override public boolean keyDown (int keycode)
    {
        if(stateManager.getCurrentState().getID() == State.StateID.MENU)
        {
            if(keycode == Input.Keys.DOWN)
            {
                MenuState currentState = (MenuState) stateManager.getCurrentState();
                currentState.setMenuPosition(currentState.getMenuPosition() + 1);
            }else if(keycode == Input.Keys.UP)
            {
                MenuState currentState = (MenuState) stateManager.getCurrentState();
                currentState.setMenuPosition(currentState.getMenuPosition() - 1);
            }else if(keycode == Input.Keys.ENTER)
            {
                MenuState currentState = (MenuState) stateManager.getCurrentState();
                switch(currentState.getMenuPosition())
                {
                    case 0:
                        stateManager.changeState(new GameState(this, font, batch, camera));
                        break;
                    case 1:
                        stateManager.changeState(new HelpState(this, font));
                        break;
                    case 2:
                        stateManager.changeState(new SettingsState(this, font));
                        break;
                    case 3:
                        Gdx.app.exit();
                        break;
                    default:
                        System.err.println("Something went wrong with the menu system");
                        break;
                }
            }
        }
        return true;
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

    public boolean isTouched() { return touched; }
    public int getXCoord() { return xCoord; }
    public int getYCoord() { return yCoord; }
}
