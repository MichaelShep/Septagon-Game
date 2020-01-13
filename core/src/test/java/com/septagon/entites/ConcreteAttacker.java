package com.septagon.entites;

import com.badlogic.gdx.graphics.Texture;

/*
 * Concrete class for testing of the Attacker class only
 * Not for use besides testing
 */

public class ConcreteAttacker extends Attacker {

    public ConcreteAttacker(int row, int col, int width, int height, Texture texture, int health, int damage, int range, String alignment)
    {
        super(row, col, width, height, texture, health, damage, range);
    }

    public void DamageFortressIfInRange(TestingFortress f){
        //If statement checks if the fortress is in range of the Attacker
        if (this.x + this.range >= f.getX() && this.x +this.range < f.getX() + f.getWidth() && this.y + this.range >= f.getY() && this.y + this.range < f.getY() + f.getHeight() ||
                this.x - this.range >= f.getX() && this.x - this.range < f.getX() + f.getWidth() && this.y - this.range >= f.getY() && this.y - this.range < f.getY() + f.getHeight()){
            f.takeDamage(this.damage);
        }
    }
}
