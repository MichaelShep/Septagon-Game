package com.septagon.entites;

/*
 * Class that will be used to define all the fortresses in the game
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fortress extends Attacker
{
    private Texture boundaryImage = new Texture(Gdx.files.internal("selected fortress.png"));
    private boolean selected = false;
    private ArrayList<Integer> rangeCorners = new ArrayList<Integer>();

    public Fortress(int x, int y, int width, int height, Texture texture, int health, int damage, int range)
    {
        super(x,y,width,height,texture, 'U', health, damage, range, "ET");
    }


    public void Improve(Boolean health, Boolean defence, Boolean damage)
    {

    }

    public void DamageEngineIfInRange(Engine e){
        if (e.getX() >= this.rangeCorners.get(0) && e.getX() < this.rangeCorners.get(1) && e.getY() >= this.rangeCorners.get(2) && e.getY() < this.rangeCorners.get(3)){
            e.takeDamage(this.damage);
            System.out.println(e.getHealth());
        }
    }

    public void initialise()
    {
        super.initialise();
        setRangeCorners();

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

    private void setRangeCorners(){
        //Makes an arrayList of the boundaries of the 2 x values and 2 y values at the corner
        Integer leftX = this.x - this.range;
        Integer rightX = this.x + this.width + this.range;
        Integer bottomY = this.y - this.range;
        Integer topY = this.y + this.height + this.range;
        this.rangeCorners.add(leftX);
        this.rangeCorners.add(rightX);
        this.rangeCorners.add(bottomY);
        this.rangeCorners.add(topY);


    }


}