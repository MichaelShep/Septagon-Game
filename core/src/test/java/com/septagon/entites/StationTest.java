package com.septagon.entites;

/*
 * A class used to test the Station class
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StationTest {
    @Test
    public void testStation() throws Exception {
        Texture testTexture = new Texture(Gdx.files.internal("images/fireStation.png"));
        Station testS = new Station(42, 6, 256, 128, testTexture, 'U');
    }

}