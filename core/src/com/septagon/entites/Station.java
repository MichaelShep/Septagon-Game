package com.septagon.entites;

/*
 * Class that will be used to define the fire stations in the game
 */

public class Station extends Entity
{
    private Boolean destroyed = false;

    public Station(int x, int y, int width, int height, int texture, char orientation)
    {
        super(x,y,width,height,texture,orientation);
    }


    public void destroy()
    {
    }
}
