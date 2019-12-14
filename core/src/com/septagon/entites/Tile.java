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
    private boolean movable = false;

    public Tile(int x, int y, int width, int height, Texture texture, char orientation, Boolean inhabitable, Boolean occupied)
    {
        super(x,y,width,height,texture,orientation);
        this.inhabitable = inhabitable;
        this.occupied = occupied;
    }


    //Returns true if the current mouse click is inside the tile at point x and y
    public boolean checkIfClickedInside(float x, float y)
    {
        float worldX = this.getX() * Tile.TILE_SIZE;
        float worldY = this.getY() * Tile.TILE_SIZE;
        if(x >= worldX && x <= worldX + Tile.TILE_SIZE && y >= worldY && y <= worldY + Tile.TILE_SIZE)
        {
            return true;
        }

        return false;

    }

    //Changes the attributes to be either movable or not movable.
    public void setMovable(boolean value) { movable = value; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }


    public boolean isMovable() { return movable; }
    public boolean isInhabitable() { return inhabitable; }
    public boolean isOccupied() { return occupied; }
}