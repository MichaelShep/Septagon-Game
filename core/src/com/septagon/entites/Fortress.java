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
    //Sets up texture that will be shown when a fortress is clicked on
    private Texture boundaryImage = new Texture(Gdx.files.internal("selected fortress.png"));
    //Stores if an engine is currently active/pressed on
    private boolean selected = false;
    //ArrayList that will store the corners of the bounding box for the range of the fortress
    private ArrayList<Integer> rangeCorners = new ArrayList<Integer>();

    /***
     * Constructor that calls the Entity constructor to set up all the member variables
     */
    public Fortress(int col, int row, int width, int height, Texture texture, int health, int damage, int range)
    {
        super(col,row, width, height, texture, health, damage, range);
    }


    /***
     * Initialise method that is used to init all variables in the class and set up everything
     * Overwritten from Attacker
     */
    public void initialise()
    {
        super.initialise();
    }

    /***
     * Method that is used to draw the fortress on the screen
     * Overwritten from Attacker
     * @param batch The batch that is used to display all objects on the screen
     */
    public void render(SpriteBatch batch)
    {
        //If the fortress is pressed, show its boundary image
        if(selected)
        {
            batch.draw(boundaryImage, (col - this.getRange()) * Tile.TILE_SIZE, (row - this.getRange()) * Tile.TILE_SIZE,
                    (((int)width / Tile.TILE_SIZE) + range * 2) * Tile.TILE_SIZE, (((int)height / Tile.TILE_SIZE) + range * 2) * Tile.TILE_SIZE);
        }
        super.render(batch);
    }

    //Getters
    public boolean isSelected() {return selected; }

    //Setters
    public void setSelected(boolean selected) { this.selected = selected; }


}