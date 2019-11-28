package com.septagon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class InputManager implements InputProcessor
{
    Vector3 tp = new Vector3();
    private boolean dragging;
    private Camera camera;

    private Boolean touched = false;
    private int xCoord;
    private int yCoord;

    public InputManager(Camera camera)
    {
        this.camera = camera;
    }

    @Override public boolean mouseMoved (int screenX, int screenY) {
        // we can also handle mouse movement without anything pressed
//		camera.unproject(tp.set(screenX, screenY, 0));
        return false;
    }

    @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        // ignore if its not left mouse button or first touch pointer
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        //camera.unproject(tp.set(screenX, screenY, 0));
        touched = !touched;
        xCoord = Gdx.input.getX();
        yCoord = Gdx.input.getY();
        dragging = true;
        return true;
    }

    @Override public boolean touchDragged (int screenX, int screenY, int pointer) {
        if (!dragging) return false;
        touched = false;
        camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY(), 0);
        camera.update();
        camera.unproject(tp.set(screenX, screenY, 0));
        return true;
    }

    @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = false;
        return true;
    }

	/*@Override public void resize (int width, int height) {
		// viewport must be updated for it to work properly
		viewport.update(width, height, true);
	}*/


    @Override public boolean keyDown (int keycode) {
        return false;
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
