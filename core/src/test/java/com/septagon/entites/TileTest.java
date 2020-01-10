package com.septagon.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * A class used to test the Tile class
 */

class TileTest {
    @Test //A test for the Tile class initialisation
    public void testTile() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Tile testT = new Tile(2, 2, testTexture, false);
        assertEquals(testT.x, 2);
        assertEquals(testT.y, 2);
        assertEquals(testT.width, 32);
        assertEquals(testT.height, 32);
        assertEquals(testT.texture, testTexture);
        assertFalse(testT.isOccupied());
    }

    @Test //A test for the Tile class' setMovable method
    public void testSetMovable() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Tile testT = new Tile(2, 2, testTexture, false);
        testT.setMovable(true);
        assertTrue(testT.isMovable());
    }

    @Test //A test for the Tile class' setOccupied method
    public void testSetOccupied() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Tile testT = new Tile(2, 2, testTexture, false);
        testT.setOccupied(true);
        assertTrue(testT.isOccupied());
    }

    @Test //A test for the Tile class' isMovable method
    public void testIsMovable() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Tile testT = new Tile(2, 2, testTexture, false);
        assertFalse(testT.isMovable());
    }

    @Test //A test for the Tile class' isOccupied method
    public void testIsOccupied() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Tile testT = new Tile(2, 2, testTexture, false);
        assertFalse(testT.isOccupied());
    }

}