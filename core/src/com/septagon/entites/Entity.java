package com.septagon.entites;

/*
 * Abstract class used to define all Entities within our game
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity
{
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Texture texture;
    protected char orientation;

    public Entity(int x, int y, int width, int height, Texture texture, char orientation)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.orientation = orientation;
    }


    //Getters
    public int getX(){ return this.x;}
    public int getY(){ return this.y; }
    public int getWidth(){ return this.width;}
    public int getHeight(){ return this.height;}
    public Texture getTexture() { return this.texture;}
    public char getOrientation() { return this.orientation;}


    //Setters
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }
    public void setTexture(Texture texture){ this.texture = texture; }
    public void setOrientation(char orientation){ this.orientation = orientation; }



    public void initialise()
    {
    }


    public void update()
    {
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(this.texture, this.x, this.y, this.width, this.height);
    }
}
