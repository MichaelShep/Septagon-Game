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
        //Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        TestingFortress testF = new TestingFortress(4, 10, 256, 256, null, 100, 20, 3);
        assertEquals(testF.x, 128);
        assertEquals(testF.y, 320);
        assertEquals(testF.col, 4);
        assertEquals(testF.row, 10);
        assertEquals(testF.width, 256);
        assertEquals(testF.height, 256);
        assertEquals(testF.texture, null);
        assertEquals(testF.health, 100);
        assertEquals(testF.damage, 20);
        assertEquals(testF.range, 3);
    }

    @Test //A test for the Fortress class' damageEngineIfInRange method
    public void testDamageEngineIfInRange() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        TestingFortress testF = new TestingFortress(4, 4, 256, 256, null, 100, 20, 3);
        //Texture testTexture2 = new Texture(Gdx.files.internal("images/engine1.png"));
        Engine testE1 = new Engine(2,2, null, 10, 2, 4, 2, 20, 4, 01);
        Engine testE2 = new Engine(3, 3, null, 10, 2, 4, 2, 20, 4, 01);
        //testF.DamageEngineIfInRange(testE1);
        //testF.DamageEngineIfInRange(testE2);
        assertEquals(testE1.health, 10);
        assertEquals(testE2.health, 10);

    }

    @Test //A test for the Fortress class' isSelected method
    public void testIsSelected() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        TestingFortress testF = new TestingFortress(4, 10, 256, 256, null, 100, 20, 3);
        assertFalse(testF.isSelected());
    }

    @Test //A test for the Fortress class' setSelected method
    public void testSetSelected() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        TestingFortress testF = new TestingFortress(4, 10, 256, 256, null, 100, 20, 3);
        testF.setSelected(true);
        assertTrue(testF.isSelected());
    }
}