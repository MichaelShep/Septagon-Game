package com.septagon.entites;

import com.badlogic.gdx.graphics.Texture;

/*
 * Concrete class for testing of the Attacker class only
 * Not for use besides testing
 */

public class ConcreteAttacker extends Attacker {

    public ConcreteAttacker(int rol, int col, int width, int height, Texture texture, int health, int damage, int range, String alignment)
    {
        super(row, col, width, height, texture, health, damage, range, alignment);
    }
}
