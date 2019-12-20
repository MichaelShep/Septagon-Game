package com.septagon.entites;

/*
 * Class that will be used to define all the fortresses in the game
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fortress extends Attacker
{
    private Texture boundaryImage = new Texture(Gdx.files.internal("selected fortress.png"));
    private boolean selected = false;

    public Fortress(int x, int y, int width, int height, Texture texture, int health, int damage, int range)
    {
        super(x,y,width,height,texture, 'U', health, damage, range, "ET");
    }


    public void Improve(Boolean health, Boolean defence, Boolean damage)
    {

    }

    public void initialise()
    {
        super.initialise();
    }

    public void render(SpriteBatch batch)
    {
        if(selected)
        {
            batch.draw(boundaryImage, (x - this.getRange()) * Tile.TILE_SIZE, (y - this.getRange()) * Tile.TILE_SIZE,
                    (((int)width / Tile.TILE_SIZE) + range * 2) * Tile.TILE_SIZE, (((int)height / Tile.TILE_SIZE) + range * 2) * Tile.TILE_SIZE);
        }
        super.render(batch);
    }

    public boolean isSelected() {return selected; }

    public void setSelected(boolean selected) { this.selected = selected; }
}