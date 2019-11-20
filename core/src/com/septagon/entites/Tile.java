package com.septagon.entites;

/*
 * Class that will be used to define all the different entities that are not
 * under the control of the player or the enemy
 */

public class Tile extends Entity 
{
    private Boolean inhabitable;
    private Boolean occupied;


    public Tile(int x, int y, int width, int height, int texture, char orientation, Boolean inhabitable, Boolean occupied)
    {
        super(x,y,width,height,texture,orientation);
        this.inhabitable = inhabitable;
        this.occupied = occupied;
    }

}