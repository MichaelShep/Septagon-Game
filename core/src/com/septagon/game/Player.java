package com.septagon.game;

/*
 * Class that will manage all the player interactions with the game
 */

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.septagon.entites.Engine;

import java.util.ArrayList;

public class Player 
{
    private ArrayList<Engine> engines = new ArrayList<Engine>();

    public void addEngine(Engine engine)
    {
        this.engines.add(engine);
    }

    public void removeEngine(Integer engineID)
    {
        int index = this.engines.indexOf(engineID);
        this.engines.remove(index);
    }

    public void update()
    {
        for(Engine e: engines)
        {
            e.update();
        }
    }

    public void render(SpriteBatch batch)
    {
        for(Engine e: engines)
        {
            e.render(batch);
        }
    }
}
