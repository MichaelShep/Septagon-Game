package com.septagon.entites;

/*
 * Concrete class for testing only
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ConcreteEntity extends Entity{

    public ConcreteEntity(int x, int y, int width, int height, Texture texture, char orientation)
    {
        super(x, y, width, height, texture, orientation);
    }
}
