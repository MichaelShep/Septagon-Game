package com.septagon.entites;

/*
 * Abstract class that provides features that extend upon entities features
 * for all entities in the game that will be able to attack other entities
 */

import com.badlogic.gdx.graphics.Texture;

public abstract class Attacker extends Entity
{
    //Variables that will store different attributes for each Attacker - unique for each instance
    protected int health;
    protected int damage;
    protected int range;

    /***
     * Constructor that sets up values based on inputs (also refers to Entity constructor)
     * @param col The map column of the Attacker
     * @param row The map row of the Attacker
     * @param texture The texture of the Attacker
     * @param health The health of the Attacker
     * @param damage The damage dealt by the Attacker
     * @param range The range of attacks for the Attacker
     */
    public Attacker(int col, int row, Texture texture, int health, int damage, int range)
    {
        super(col,row,Tile.TILE_SIZE,Tile.TILE_SIZE,texture);
        this.health = health;
        this.damage = damage;
        this.range = range;
    }

    /***
     * Method that will check if the Attacker is in range of the fortress and if so will damage it
     * @param f The fortress we are currently checking the bounds/range of
     */
    public void DamageFortressIfInRange(Fortress f){
        //If statement checks if the fortress is in range of the Attacker
        if (this.x + this.range >= f.getX() && this.x +this.range < f.getX() + f.getWidth() && this.y + this.range >= f.getY() && this.y + this.range < f.getY() + f.getHeight() ||
                this.x - this.range >= f.getX() && this.x - this.range < f.getX() + f.getWidth() && this.y - this.range >= f.getY() && this.y - this.range < f.getY() + f.getHeight()){
            f.takeDamage(this.damage);
        }
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
