package com.septagon.entites;

/*
 * Vehicle class that will be used to define all vehicles within the game
 */

import com.badlogic.gdx.graphics.Texture;

public class Vehicle extends Attacker
{
    protected int speed;
    protected char direction;

    public Vehicle (int x, int y, int width, int height, Texture texture, char orientation, int health, int damage, int range, String alignment, int speed, char direction)
    {
        super(x,y,width,height,texture,orientation,health,damage,range,alignment);
        this.speed = speed;
        this.direction = direction;
    }

    public void move(Character direction, int distance)
    {
    }

    public int getSpeed() { return speed; }
    public char getDirection() { return direction; }
}
