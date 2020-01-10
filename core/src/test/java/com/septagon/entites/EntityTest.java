package com.septagon.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * A class used to test the Entity class
 * * Uses the concrete version of the Entity class to enable testing
 */

class EntityTest {
    @Test //A test for the Entity class initialisation
    public void testEntity() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        assertEquals(testEN.col, 5);
        assertEquals(testEN.row, 10);
        assertEquals(testEN.x, 160);
        assertEquals(testEN.y, 320);
        assertEquals(testEN.width, 64);
        assertEquals(testEN.height, 64);
        assertEquals(testEN.texture, testTexture);
    }

    @Test //A test for the Entity class' getX method
    public void testGetX() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        assertEquals(testEN.getX(), 5);
    }

    @Test //A test for the Entity class' getY method
    public void testGetY() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        assertEquals(testEN.getY(), 10);
    }

    @Test //A test for the Entity class' getWidth method
    public void testGetWidth() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        assertEquals(testEN.getWidth(), 64);
    }

    @Test //A test for the Entity class' getHeight method
    public void testGetHeight() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        assertEquals(testEN.getHeight(), 64);
    }

    @Test //A test for the Entity class' getTexture method
    public void testGetTexture() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        assertEquals(testEN.getTexture(), testTexture);
    }

    /*
    @Test //A test for the Entity class' getOrientation method
    public void testGetOrientation() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        assertEquals(testEN.getOrientation(), 'U');
    }
     */

    @Test //A test for the Entity class' setX method
    public void testSetX() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        testEN.setX(32);
        assertEquals(testEN.x, 32);
        assertEquals(testEN.col, 1);
    }

    @Test //A test for the Entity class' setY method
    public void testSetY() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        testEN.setY(32);
        assertEquals(testEN.y, 32);
        assertEquals(testEN.row, 1);
    }

    @Test //A test for the Entity class' setY method
    public void testSetCol() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        testEN.setCol(1);
        assertEquals(testEN.x, 32);
        assertEquals(testEN.row, 1);
    }

    @Test //A test for the Entity class' setY method
    public void testSetRow() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        testEN.setRow(32);
        assertEquals(testEN.y, 32);
        assertEquals(testEN.row, 1);
    }

    @Test //A test for the Entity class' setTexture method
    public void testSetTexture() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        Texture testTexture2 = new Texture(Gdx.files.internal("images/engine2.png"));
        testEN.setTexture(testTexture2);
        assertEquals(testEN.texture, testTexture2);
    }
    /*
    @Test //A test for the Entity class' setOrientation method
    public void testSetOrientation() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture);
        testEN.setOrientation('D');
        assertEquals(testEN.orientation, 'D');
    }
     */
}