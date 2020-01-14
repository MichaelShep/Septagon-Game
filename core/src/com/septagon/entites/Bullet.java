package com.septagon.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {

    //public static final int SPEED = 500;
    public static double ySPEED = 500;
    public static double xSPEED = 500;
    public static Texture texture;
    private static double deltaY, deltaX;

    float x, y, targetX, targetY;
    public boolean remove = false;
    //creates a bullet with attacker and target positions
    public Bullet (float attackerX, float attackerY, float targetX, float targetY) {
        this.x = attackerX;
        this.y = attackerY;
        this.targetX = targetX;
        this.targetY = targetY;
        deltaY = targetY - attackerY;
        deltaX = targetX - attackerX;

        //calculate relative speed in both x and y directions in order to move from attacker to target
        ySPEED = deltaY / (deltaX*deltaX + deltaY*deltaY);
        xSPEED = deltaX / (deltaX*deltaX + deltaY*deltaY);
        if (texture == null) {
            texture = new Texture("bullet.png");
        }
    }

    //move bullet appropriately in both directions
    public void update (float deltaTime) {
        y += ySPEED * deltaTime * 50000;
        x += xSPEED * deltaTime * 50000;
        if ((deltaX* (targetX - x) < 0)&&(deltaY* (targetY - y) < 0))
            remove = true;
        System.out.println(deltaX* (targetX - x));
    }

    public void render (SpriteBatch batch){
        batch.draw(texture, x, y);
    }
}
