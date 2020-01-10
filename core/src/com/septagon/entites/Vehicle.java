package com.septagon.entites;

/*
 * Vehicle class that will be used to define all vehicles within the game
 */

import com.badlogic.gdx.graphics.Texture;

public class Vehicle extends Attacker
{
    protected int speed;
    protected char direction;

    public Vehicle (int col, int row, Texture texture, int health, int damage, int range, int speed)
    {
        super(col,row, Tile.TILE_SIZE, Tile.TILE_SIZE, texture,health,damage,range);
        this.speed = speed;
    }

    /***
     * Method that will control movement of vehicle - will be overwritten by subclasses where needed
     * @param direction The direcion of the movement
     * @param distance How far the vehicle should move
     */
    public void move(Character direction, int distance)
    {
    }

    //Getters
    public int getSpeed() { return speed; }
    public char getDirection() { return direction; }
}
