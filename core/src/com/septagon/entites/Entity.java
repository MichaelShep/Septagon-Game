package com.septagon.entites;

/*
 * Abstract class used to define all Entities within our game
 */

public abstract class Entity
{
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int texture;
    protected char orientation;

    public Entity(int x, int y, int width, int height, int texture, char orientation)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.orientation = orientation;
    }

    public void initialise()
    {
    }


    public void update()
    {
    }

    public void render()
    {
    }
}
