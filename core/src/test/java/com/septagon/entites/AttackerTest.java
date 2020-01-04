package com.septagon.entites;

/*
 * A class used to test the Attacker class
 * Uses the concrete version of the Attacker class to enable testing
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
import  static org.junit.jupiter.api.Assertions.*;

class AttackerTest {
    @Test
    public void testAttacker() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 'U', 10, 2, 4, "Friendly");
        assertEquals(testCA.x, 0);
        assertEquals(testCA.y, 0);
        assertEquals(testCA.width, 32);
        assertEquals(testCA.height, 32);
        assertEquals(testCA.texture, testTexture);
        assertEquals(testCA.orientation, 'U');
        assertEquals(testCA.health, 10);
        assertEquals(testCA.damage, 2);
        assertEquals(testCA.range, 4);
        assertEquals(testCA.alignment, "Friendly");
    }

    @Test
    public void testDamageFortressIfInRange() throws Exception {

    }

    @Test
    public void testGetHealth() throws Exception {

    }

    @Test
    public void testTakeDamage() throws Exception {

    }

    @Test
    public void testGetDamage() throws Exception {

    }

    @Test
    public void testSetDamage() throws Exception {

    }

    @Test
    public void testGetRange() throws Exception {

    }

    @Test
    public void testSetRange() throws Exception {

    }

    @Test
    public void testGetAlignment() throws Exception {

    }

    @Test
    public void testSetAlignment() throws Exception {

    }
}

