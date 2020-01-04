package com.septagon.entites;

/*
 * Class that will be used to keep track of and handle the processing
 * of all entities in the game
 */

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class EntityManager
{
    private ArrayList<Entity> entities;

    public EntityManager()
    {
        entities = new ArrayList<Entity>();
    }

    public void addEntity(Entity newEntity)
    {
        entities.add(newEntity);
    }

    public void removeEntity(Entity entityToRemove)
    {
        if(entities.contains(entityToRemove))
            entities.remove(entityToRemove);
    }

    public void initialise()
    {
        for(Entity e: entities)
            e.initialise();
    }

    public void update()
    {
        for(Entity e: entities)
            e.update();
    }


    public void render(SpriteBatch batch)
    {
        for(Entity e: entities)
            e.render(batch);
    }
}
