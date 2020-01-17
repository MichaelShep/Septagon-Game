package com.septagon.entites;

/*
 * Class that inherits from vehicle that will define all the fire engines
 * in the game
 */

import com.badlogic.gdx.graphics.Texture;
import com.septagon.states.GameState;

public class Engine extends Vehicle
{
    //Member variables that will be unique stats of each engine
    private int volume;
    private int maxVolume;
    private int fillSpeed;
    private Integer id;

    //Keeps track of whether the engine has moved on the current player turn
    private boolean moved = false;

    /***
     * Constructor that Sets up the member variables for engine
     * @param col The column in the map that the engine is at
     * @param row The row in the map that the engine is at
     * @param texture The texture of the engine
     * @param health The health of the engine - unique to engine
     * @param damage The damage of the engine - unique to engine
     * @param range The range of the engine - unique to engine
     * @param speed The speed of the engine - unique to engine
     * @param maxVolume The maximum volume of the engine - unique to engine
     * @param fillSpeed The fill speed of the engine - unique to engine
     * @param id The id of the engine - unique to engine
     */
    public Engine(int col, int row, Texture texture, int health, int damage, int range, int speed, int maxVolume, int fillSpeed, Integer id) {
        super(col, row, texture, health, damage, range, speed);
        this.volume = maxVolume;
        this.maxVolume = maxVolume;
        this.fillSpeed = fillSpeed;
        this.id = id;
    }

    /***
     * Method that will be called when the engine is at a station so that it can increase its volume
     */
    public void fill()
    {
            if (this.volume <= (this.maxVolume - this.fillSpeed)) {
                this.volume += this.fillSpeed;
            }else {
                this.volume = this.maxVolume;
            }
    }

    /***
     * Checks if the engine is in range to fill and calls the fill method if it is
     * @param s The Fire Station
     */
    public void ifInRangeFill(Station s){
        if(checkForOverlap(s)){
            fill();
        }
    }

    public void fire(){
        this.volume -= this.damage;
    }



    /***
     * Checks if any of the corners of the engines range are in the body of the fortress
     * @param e Fortress that is being checked
     * @return returns true if there is any overlap, false otherwise
     */
    public Boolean checkForOverlap(Entity e){
        for(int i=0; i<2; i++){
            for(int j=2; j<4; j++){
                if (rangeCorners.get(i) >= e.getCol() && rangeCorners.get(i) < e.getCol() + (e.getWidth()/Tile.TILE_SIZE)
                        && rangeCorners.get(j) >= e.getRow() && rangeCorners.get(j) < e.getRow() + (e.getHeight()/Tile.TILE_SIZE)){
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * Method that will check if the Attacker is in range of the fortress and if so will damage it
     * @param f The fortress we are currently checking the bounds/range of
     */
    public void DamageFortressIfInRange(Fortress f){
        this.setRangeCorners();
        System.out.println(getRangeCorners());
        if(checkForOverlap(f)){
            if (this.volume >= this.damage){
                this.fire();
                f.takeDamage(this.damage);
                GameState.bullets.add(new Bullet(this.x + 20, this.y + 10, f.x + 150, f.y + 50, true));
                GameState.bullets.add(new Bullet(this.x, this.y, f.x + 150, f.y + 50, true));
                GameState.bullets.add(new Bullet(this.x + 40, this.y + 20, f.x + 150, f.y + 50, true));
            }
        }

    }

    //Getters and Setters
    public int getMaxVolume()
    {
        return this.maxVolume;
    }
    public int getVolume() { return this.volume; }
    public int getFillSpeed() { return fillSpeed; }

    public Integer getID()
    {
        return this.id;
    }

    public boolean isMoved(){return this.moved;}

    public void setMoved(boolean moved){this.moved = moved;}
    public void setVolume(int volume) { this.volume = volume; }
    public void setFillSpeed(int fillSpeed) { this.fillSpeed = fillSpeed; }




}