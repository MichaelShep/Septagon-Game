package com.septagon.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import  static org.junit.jupiter.api.Assertions.*;

/*
 * A class used to test the Attacker class
 * Uses the concrete version of the Attacker class to enable testing
 */

class AttackerTest {
    @Test //A test for the Attacker class initialisation
    public void testAttacker() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        assertEquals(testCA.x, 0);
        assertEquals(testCA.y, 0);
        assertEquals(testCA.width, 32);
        assertEquals(testCA.height, 32);
        assertEquals(testCA.texture, testTexture);
        assertEquals(testCA.health, 10);
        assertEquals(testCA.damage, 2);
        assertEquals(testCA.range, 4);
        assertEquals(testCA.alignment, "Friendly");
    }

    @Test //A test for the Attacker class' damageFortressIfInRange method
    public void testDamageFortressIfInRange() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(1, 1, 32, 32, testTexture, 10, 2, 4, "Friendly");
        Texture testTexture2 = new Texture(Gdx.files.internal("images/FortressMinister.png"));
        Fortress testF1 = new Fortress(4, 4, 256, 256, testTexture2, 100, 20, 3);
        Fortress testF2 = new Fortress(10, 10, 256, 256, testTexture2, 100, 20, 3);
        testCA.DamageFortressIfInRange(testF1);
        testCA.DamageFortressIfInRange(testF2);
        assertEquals(testF2, 100);
        assertNotEquals(testF1, 100);
    }

    @Test //A test for the Attacker class' getHealth method
    public void testGetHealth() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        assertEquals(testCA.getHealth(), 10);
    }

    @Test //A test for the Attacker class' takeDamage method
    public void testTakeDamage() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        testCA.takeDamage(6);
        assertEquals(testCA.health, 4);
    }

    @Test //A test for the Attacker class' getDamage method
    public void testGetDamage() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        assertEquals(testCA.getDamage(), 2);
    }

    @Test //A test for the Attacker class' setDamage method
    public void testSetDamage() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        testCA.setDamage(3);
        assertEquals(testCA.damage, 3);
    }

    @Test //A test for the Attacker class' getRange method
    public void testGetRange() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        assertEquals(testCA.getRange(), 4);
    }

    @Test //A test for the Attacker class' setRange method
    public void testSetRange() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        testCA.setRange(3);
        assertEquals(testCA.range, 3);
    }

    @Test //A test for the Attacker class' getAlignment method
    public void testGetAlignment() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        assertEquals(testCA.alignment, "Friendly");
    }

    @Test //A test for the Attacker class' setAlignment method
    public void testSetAlignment() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteAttacker testCA = new ConcreteAttacker(0, 0, 32, 32, testTexture, 10, 2, 4, "Friendly");
        testCA.setAlignment("Enemy");
        assertEquals(testCA.alignment, "Enemy");
    }
}

