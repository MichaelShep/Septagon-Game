package com.septagon.entites;
/*
 * Abstract class that provides features that extend upon entities features
 * for all entities in the game that will be able to attack other entities
 */

import com.badlogic.gdx.graphics.Texture;
import com.septagon.states.GameState;

import java.util.ArrayList;
import java.util.List;

public abstract class Attacker extends Entity
{
    //Variables that will store different attributes for each Attacker - unique for each instance
    protected int health;
    protected int maxHealth;
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
    public Attacker(int col, int row, int width, int height, Texture texture, int health, int damage, int range)
    {
        super(col,row,width,height,texture);
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.range = range;
    }

    private ArrayList<Integer> rangeCorners = new ArrayList<Integer>();

    /***
     * Method that will check if the Attacker is in range of the fortress and if so will damage it
     * @param f The fortress we are currently checking the bounds/range of
     */
    public void DamageFortressIfInRange(Fortress f){
            this.setRangeCorners();
            if(checkForOverlap(f)){
                System.out.println("Fortress Damaged");
                f.takeDamage(this.damage);
            }

    }

    /***
     * checks if any of the corners of the engines range are in the body of the fortress
     * @param f Fortress that is being checked
     * @return returns true if there is any overlap, false otherwise
     */
    private Boolean checkForOverlap(Fortress f){
        for(int i=0; i<2; i++){
            for(int j=2; i<4; i++){
                if (rangeCorners.get(i) >= f.getCol() && rangeCorners.get(i) < f.getCol() + f.getWidth()/Tile.TILE_SIZE && rangeCorners.get(j) >= f.getRow() && rangeCorners.get(j) < f.getRow() + f.getHeight()/Tile.TILE_SIZE){
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * Method that will be called if an engine is in range of the fortress so that the engine can be damaged
     * @param e The current engine that is being checked
     */
    public void DamageEngineIfInRange(Engine e){
        if (e.getCol() >= this.rangeCorners.get(0) && e.getCol() < this.rangeCorners.get(1) && e.getRow() >= this.rangeCorners.get(2) && e.getRow() < this.rangeCorners.get(3)){
            e.takeDamage(this.damage);
            GameState.bullets.add(new Bullet(this.x + 150, this.y + 50, e.x + 20, e.y + 10));
        }
    }


    @Override
    public void initialise() {
        super.initialise();
        setRangeCorners();
    }

    /***
     * Overriding of the update method, used to make sure an attackers health never passes below 0
     */
    public void update(){
        super.update();
        if(health <= 0) health = 0;
    }

    private void setRangeCorners() {
        //Makes an arrayList of the boundaries of the 2 x values and 2 y values at the corner
        Integer leftX = this.col - this.range;
        Integer rightX = this.col + this.width/Tile.TILE_SIZE + this.range;
        Integer bottomY = this.row - this.range;
        Integer topY = this.row + this.height/Tile.TILE_SIZE + this.range;
        this.rangeCorners.add(leftX);
        this.rangeCorners.add(rightX);
        this.rangeCorners.add(bottomY);
        this.rangeCorners.add(topY);
    }

    //Getters
    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }
    public int getRange() {
        return range;
    }
    public int getMaxHealth() {
        return maxHealth;
    }

    //Setters
    public void takeDamage(int damage) {
        this.health -= damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setRange(int range) {
        this.range = range;
    }
    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }

    public void setHealth(int health){
        this.health = health;
    }
}