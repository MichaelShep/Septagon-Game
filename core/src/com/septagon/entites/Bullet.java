package com.septagon.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {

    //public static final int SPEED = 500;
    public static float ySPEED = 500;
    public static float xSPEED = 500;
    public static Texture texture;

    float x, y, targetX, targetY;
    public boolean remove = false;
    //creates a bullet with attacker and target positions
    public Bullet (float attackerX, float attackerY, float targetX, float targetY) {
        this.x = attackerX;
        this.y = attackerY;
        this.targetX = targetX;
        this.targetY = targetY;
        float deltaY = attackerY - targetY;
        float deltaX = attackerX - targetX;

        //calculate relative speed in both x and y directions in order to move from attacker to target
        ySPEED = deltaY/(deltaX+deltaY);
        xSPEED = deltaX/(deltaX+deltaY);
        if (texture == null) {
            texture = new Texture("bullet.png");
        }
        System.out.println("xspeed:" + xSPEED);
        System.out.println("yspeed:" + ySPEED);
    }

    //move bullet appropriately in both directions
    public void update (float deltaTime) {
        y += ySPEED * deltaTime * 500;
        x += xSPEED * deltaTime * 500;
        if (x * (x + xSPEED * deltaTime * 500) < 0)
            remove = true;
    }

    public void render (SpriteBatch batch){
        batch.draw(texture, x, y);
    }
}
