package com.septagon.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * A class used to test the Fortress class
 */

class FortressTest {
    @Test //A test for the Fortress class initialisation
    public void testFortress() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        Fortress testF = new Fortress(4, 10, 256, 256, testTexture, 100, 20, 3);
        assertEquals(testF.x, 4);
        assertEquals(testF.y, 10);
        assertEquals(testF.width, 256);
        assertEquals(testF.height, 256);
        assertEquals(testF.texture, testTexture);
        assertEquals(testF.health, 100);
        assertEquals(testF.damage, 20);
        assertEquals(testF.range, 3);
        assertEquals(testF.alignment, "ET");
    }

    @Test //A test for the Fortress class' damageEngineIfInRange method
    public void testDamageEngineIfInRange() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        Fortress testF = new Fortress(4, 4, 256, 256, testTexture, 100, 20, 3);
        Texture testTexture2 = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE1 = new Engine(5,5,32,32, testTexture2, 10, 2, 4, "Friendly", 2, 'U', 20, 20, 4, 01);
        Engine testE = new Engine(10,10,32,32, testTexture2, 10, 2, 4, "Friendly", 2, 'U', 20, 20, 4, 01);

    }

    @Test //A test for the Fortress class' isSelected method
    public void testIsSelected() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        Fortress testF = new Fortress(4, 10, 256, 256, testTexture, 100, 20, 3);
        assertFalse(testF.isSelected());
    }

    @Test //A test for the Fortress class' setSelected method
    public void testSetSelected() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        Fortress testF = new Fortress(4, 10, 256, 256, testTexture, 100, 20, 3);
        testF.setSelected(true);
        assertTrue(testF.isSelected());
    }
}