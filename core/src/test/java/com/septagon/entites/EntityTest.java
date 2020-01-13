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
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        assertEquals(testEN.col, 5);
        assertEquals(testEN.row, 10);
        assertEquals(testEN.x, 160);
        assertEquals(testEN.y, 320);
        assertEquals(testEN.width, 64);
        assertEquals(testEN.height, 64);
        assertEquals(testEN.texture, null);
    }

    @Test //A test for the Entity class' getX method
    public void testGetX() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        assertEquals(testEN.getX(), 160);
    }

    @Test //A test for the Entity class' getY method
    public void testGetY() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        assertEquals(testEN.getY(), 320);
    }

    @Test //A test for the Entity class' getWidth method
    public void testGetWidth() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        assertEquals(testEN.getWidth(), 64);
    }

    @Test //A test for the Entity class' getHeight method
    public void testGetHeight() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        assertEquals(testEN.getHeight(), 64);
    }

    @Test //A test for the Entity class' getTexture method
    public void testGetTexture() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        assertEquals(testEN.getTexture(), null);
    }

    @Test //A test for the Entity class' setX method
    public void testSetX() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        testEN.setX(32);
        assertEquals(testEN.x, 32);
        assertEquals(testEN.col, 1);
    }

    @Test //A test for the Entity class' setY method
    public void testSetY() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        testEN.setY(32);
        assertEquals(testEN.y, 32);
        assertEquals(testEN.row, 1);
    }

    @Test //A test for the Entity class' setCol method
    public void testSetCol() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        testEN.setCol(1);
        assertEquals(testEN.x, 32);
        assertEquals(testEN.row, 10);
    }

    @Test //A test for the Entity class' setRow method
    public void testSetRow() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        testEN.setRow(1);
        assertEquals(testEN.y, 32);
        assertEquals(testEN.row, 1);
    }

    @Test //A test for the Entity class' setTexture method
    public void testSetTexture() throws Exception {
        //Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, null);
        //Texture testTexture2 = new Texture(Gdx.files.internal("images/engine2.png"));
        testEN.setTexture(null);
        assertEquals(testEN.texture, null);
    }
}