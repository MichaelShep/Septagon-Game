package com.septagon.entites;

/*
 * Class that inherits from vehicle that will define all the fire engines
 * in the game
 */

import com.badlogic.gdx.graphics.Texture;

public class Engine extends Vehicle
{
    private int volume;
    private int maxVolume;
    private int fillSpeed;
    private Integer id;
    private boolean moved = false;

    public Engine(int x, int y, int width, int height, Texture texture, char orientation, int health, int damage, int range, String alignment, int speed, char direction, int volume, int maxVolume, int fillSpeed, Integer id)
    {
        super(x,y,width,height,texture,orientation,health,damage,range,alignment,speed,direction);
        this.volume = volume;
        this.maxVolume = maxVolume;
        this.fillSpeed = fillSpeed;
        this.id = id;
    }

    /* When fire engine is in range of the station it will refill
    at the rate defined in fillSpeed*/
    public void fill()
    {
        if (this.volume <= (this.maxVolume - this.fillSpeed)) 
        {
            this.volume += this.fillSpeed;
        }else
        {
            this.volume = this.maxVolume;
        }

    }

    public int getMaxVolume()
    {
        return this.maxVolume;
    }

    public Integer getID()
    {
        return this.id;
    }

    public boolean isMoved(){return this.moved;}

    public void setMoved(boolean moved){this.moved = moved;}




}