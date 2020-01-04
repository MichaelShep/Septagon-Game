package com.septagon.entites;

/*
 * Class that will be used to define the fire stations in the game
 */

import com.badlogic.gdx.graphics.Texture;

public class Station extends Entity
{
    private Boolean destroyed = false;

    public Station(int x, int y, int width, int height, Texture texture)
    {
        super(x,y,width,height,texture);
    }


    public void destroy()
    {
    }
}
