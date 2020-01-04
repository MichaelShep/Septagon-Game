package com.septagon.entites;

/*
 * A class used to test the Vehicle class
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    @Test
    public void testVehicle() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/engine1.png"));
        Vehicle testV = new Vehicle(0,0,32,32, testTexture,'U', 10, 2, 4, "Friendly", 2, 'U');
    }

    @Test
    public void testMove() throws Exception {

    }

    @Test
    public void testGetSpeed() throws Exception {

    }
    @Test
    public void testGetDirection() throws Exception {

    }

}