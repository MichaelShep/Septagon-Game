package com.septagon.entites;

/*
 * Abstract class that provides features that extend upon entities features
 * for all entities in the game that will be able to attack other entities
 */

import com.badlogic.gdx.graphics.Texture;

public abstract class Attacker extends Entity
{
    protected int health;
    protected int damage;
    protected int range;
    protected String alignment;


    public Attacker(int x, int y, int width, int height, Texture texture, char orientation, int health, int damage, int range, String alignment)
    {
        super(x,y,width,height,texture,orientation);
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.alignment = alignment;
    }

    public void destroy()
    {

    }
}
