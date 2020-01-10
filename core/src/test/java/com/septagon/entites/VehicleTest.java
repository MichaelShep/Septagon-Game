package com.septagon.entites;

/*
 * A class used to test the Vehicle class
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    @Test //A test for the Vehicle class initialisation
    public void testVehicle() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Vehicle testV = new Vehicle(0,0, testTexture, 10, 2, 4, 2);
        assertEquals(testV.x, 0);
        assertEquals(testV.y, 0);
        assertEquals(testV.width, 32);
        assertEquals(testV.height, 32);
        assertEquals(testV.texture, testTexture);
        assertEquals(testV.health, 10);
        assertEquals(testV.damage, 2);
        assertEquals(testV.range, 4);
        assertEquals(testV.speed, 2);
        assertEquals(testV.direction, 'U');
    }

    @Test //A test for the Vehicle class' getSpeed method
    public void testGetSpeed() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Vehicle testV = new Vehicle(0,0, testTexture, 10, 2, 4, 2);
        assertEquals(testV.getSpeed(), 2);
    }

    @Test //A test for the Vehicle class' getDirection method
    public void testGetDirection() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Vehicle testV = new Vehicle(0,0, testTexture, 10, 2, 4, 2);
        assertEquals(testV.getDirection(), 'U');
    }

}