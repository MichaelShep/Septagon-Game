package com.septagon.entites;

/*
 * Class that will be used to define all the fortresses in the game
 */

import com.badlogic.gdx.graphics.Texture;

public class Fortress extends Attacker
{
    public Fortress(int x, int y, int width, int height, Texture texture, int health, int damage, int range)
    {
        super(x,y,width,height,texture, 'U', health, damage, range, "ET");
    }


    public void Improve(Boolean health, Boolean defence, Boolean damage)
    {

    }

}