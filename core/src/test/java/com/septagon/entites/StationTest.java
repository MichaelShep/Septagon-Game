package com.septagon.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * A class used to test the Station class
 */

class StationTest {
    @Test //A test for the Station class initialisation
    public void testStation() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/fireStation.png"));
        Station testS = new Station(42, 6, 256, 128, null);
        assertEquals(testS.col, 42);
        assertEquals(testS.row, 6);
        assertEquals(testS.x, 1344);
        assertEquals(testS.y, 192);
        assertEquals(testS.width, 256);
        assertEquals(testS.height, 128);
        assertEquals(testS.texture, null);
    }

}