package com.septagon.entites;

/*
 * Concrete class for testing only
 */

import com.badlogic.gdx.graphics.Texture;

public class ConcreteAttacker extends Attacker {

    public ConcreteAttacker(int x, int y, int width, int height, Texture texture, char orientation, int health, int damage, int range, String alignment)
    {
        super(x, y, width, height, texture, orientation, health, damage, range, alignment);
    }
}
