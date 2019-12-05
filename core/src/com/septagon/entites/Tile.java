package com.septagon.entites;

/*
 * Class that will be used to define all the different entities that are not
 * under the control of the player or the enemy
 */

import com.badlogic.gdx.graphics.Texture;

public class Tile extends Entity
{
	public static final int TILE_SIZE = 32;
	
    private boolean inhabitable;
    private boolean occupied;


    public Tile(int x, int y, int width, int height, Texture texture, char orientation, Boolean inhabitable, Boolean occupied)
    {
        super(x,y,width,height,texture,orientation);
        this.inhabitable = inhabitable;
        this.occupied = occupied;
    }

    public void checkIfIntersectedWith(float x, float y)
    {
        if(x >= this.getX() && x <= this.getX() + this.getWidth() && y >= this.getY() && y <= this.getY() + this.getHeight())
        {
            System.out.println("Tile X: " + getX() / Tile.TILE_SIZE + ", Tile Y: " + getY() / Tile.TILE_SIZE);
        }
    }
}