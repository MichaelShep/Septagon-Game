package com.septagon.entites;

/*
 * Class that will be used to define all the fortresses in the game
 */

public class Fortress extends Attacker 
{
    public Fortress(int x, int y, int width, int height, int texture, char orientation, int health, int damage, int range, String alignment)
    {
        super(x,y,width,height,texture,orientation,health,damage,range,alignment);
    }


    public void Improve(Boolean health, Boolean defence, Boolean damage)
    {

    }

}