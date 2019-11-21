package com.septagon.entites;

/*
 * Class that will be used to define all the fortresses in the game
 */

import com.badlogic.gdx.graphics.Texture;

public class Fortress extends Attacker
{
    public Fortress(int x, int y, int width, int height, Texture texture, char orientation, int health, int damage, int range, String alignment)
    {
        super(x,y,width,height,texture,orientation,health,damage,range,alignment);
    }


    public void Improve(Boolean health, Boolean defence, Boolean damage)
    {

    }

}