package com.septagon.entites;

/*
 * A class used to test the Entity class
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    @Test
    public void testEntity() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        ConcreteEntity testEN = new ConcreteEntity(5, 10, 64, 64, testTexture, 'U');
    }

    @Test
    public void testGetX() throws Exception {

    }

    @Test
    public void testGetY() throws Exception {

    }

    @Test
    public void testGetWidth() throws Exception {

    }

    @Test
    public void testGetHeight() throws Exception {

    }

    @Test
    public void testGetTexture() throws Exception {

    }

    @Test
    public void testGetOrientation() throws Exception {

    }

    @Test
    public void testSetX() throws Exception {

    }

    @Test
    public void testSetY() throws Exception {

    }

    @Test
    public void testSetTexture() throws Exception {

    }

    @Test
    public void testSetOrientation() throws Exception {

    }

}