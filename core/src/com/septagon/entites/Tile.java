package com.septagon.entites;

/*
 * Class that will be used to define all the different entities that are not
 * under the control of the player or the enemy
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile extends Entity
{
	public static final int TILE_SIZE = 32;
	
    private boolean inhabitable;
    private boolean occupied;
    private TileType type;


    public Tile(int x, int y, int width, int height, Texture texture, char orientation, Boolean inhabitable, Boolean occupied, TileType type)
    {
        super(x,y,width,height,texture,orientation);
        this.inhabitable = inhabitable;
        this.occupied = occupied;
        this.type = type;
    }
    
    public void render(SpriteBatch batch)
    {
    	batch.draw(type.getTexture(), x, y, width, height);
    }
    
    public TileType getType()
    {
    	return type;
    }

}