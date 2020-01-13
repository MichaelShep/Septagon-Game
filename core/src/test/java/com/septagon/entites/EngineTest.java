package com.septagon.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * A class used to test the Engine class
 */

class EngineTest {
    @Test  //A test for the Engine class initialisation
    public void testEngine() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0,0, null, 10, 2, 4, 2, 20, 4, 01);
        assertEquals(testE.x, 0);
        assertEquals(testE.y, 0);
        assertEquals(testE.width, 32);
        assertEquals(testE.height, 32);
        assertEquals(testE.texture, null);
        assertEquals(testE.health, 10);
        assertEquals(testE.damage, 2);
        assertEquals(testE.range, 4);
        assertEquals(testE.speed, 2);
        assertEquals(testE.getMaxVolume(), 20);
        assertEquals(testE.getID(), 01);
        assertEquals(testE.getVolume(), 20);
        assertEquals(testE.getFillSpeed(), 4);

    }

    @Test
    public void testFill() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0,0, null, 10, 2, 4, 2, 20, 4, 01);
        testE.fill();
        assertEquals(testE.getVolume(), 20);
        //testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        testE = new Engine(0,0,null, 10, 2, 4, 2, 20, 4, 01);
        assertEquals(testE.getVolume(), 20);
    }

    @Test //A test for the Engine class' getMaxVolume method
    public void testGetMaxVolume() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0,0, null, 10, 2, 4, 2, 20, 4, 01);
        assertEquals(testE.getMaxVolume(), 20);
    }

    @Test //A test for the Engine class' getID method
    public void testGetID() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0,0, null, 10, 2, 4, 2, 20, 4, 01);
        assertEquals(testE.getID(), 01);
    }

    @Test //A test for the Engine class' isMoved method
    public void testIsMoved() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0,0, null, 10, 2, 4, 2, 20, 4, 01);
        assertFalse(testE.isMoved());
    }

    @Test //A test for the Engine class' setMoved method
    public void testSetMoved() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0,0, null, 10, 2, 4, 2, 20, 4, 01);
        testE.setMoved(true);
        assertTrue(testE.isMoved());
    }

    @Test //A test for the Engine class' getID method
    public void testGetVolume() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0,0, null, 10, 2, 4, 2, 20, 4, 01);
        assertEquals(testE.getVolume(), 20);
    }

    @Test //A test for the Engine class' getID method
    public void testGetFillSpeed() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE = new Engine(0, 0, null, 10, 2, 4, 2, 20, 4, 01);
        assertEquals(testE.getFillSpeed(), 4);
    }

}