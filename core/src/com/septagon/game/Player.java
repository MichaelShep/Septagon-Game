package com.septagon.game;

/*
 * Class that will manage all the player interactions with the game
 */

import com.septagon.entites.Engine;

import java.util.ArrayList;

public class Player 
{
    private ArrayList<Integer> engines = new ArrayList<Integer>();

    public void addEngine(Engine engine)
    {
        Integer id = engine.getID();
        this.engines.add(id);
    }

    public void removeEngine(Integer engineID)
    {
        int index = this.engines.indexOf(engineID);
        this.engines.remove(index);
    }

}
