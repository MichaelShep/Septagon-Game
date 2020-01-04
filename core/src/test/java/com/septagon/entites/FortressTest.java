package com.septagon.entites;

/*
 * A class used to test the Fortress class
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FortressTest {
    @Test
    public void testFortress() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        Fortress testF = new Fortress(4, 10, 256, 256, testTexture, 100, 20, 3);
    }

    @Test
    public void testDamageEngineIfInRange() throws Exception {

    }

    @Test
    public void testIsSelected() throws Exception {

    }

    @Test
    public void testSetSelected() throws Exception {

    }

    @Test
    public void testSetRangeCorners() throws Exception {

    }

}