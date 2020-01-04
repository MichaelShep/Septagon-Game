package com.septagon.entites;

/*
 * A class used to test the Engine class
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.septagon.entites.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EngineTest {
    @Test
    public void testEngine() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0,0,32,32, testTexture,'U', 10, 2, 4, "Friendly", 2, 'U', 20, 20, 4, 01);

    }

    @Test
    public void testFill() throws Exception {

    }

    @Test
    public void testGetMaxVolume() throws Exception {

    }

    @Test
    public void testGetID() throws Exception {

    }

    @Test
    public void testIsMoved() throws Exception {

    }

    @Test
    public void testSetMoved() throws Exception {

    }

}