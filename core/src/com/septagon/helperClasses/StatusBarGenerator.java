package com.septagon.helperClasses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.septagon.entites.Attacker;
import com.septagon.entites.Engine;
import com.septagon.entites.Fortress;

import java.util.ArrayList;

public class StatusBarGenerator
{
    private ShapeRenderer barRenderer;
    private ArrayList<Engine> engines;
    private ArrayList<Fortress> fortresses;

    public StatusBarGenerator(ArrayList<Engine> engines, ArrayList<Fortress> fortresses){
        this.engines = engines;
        this.fortresses = fortresses;
        barRenderer = new ShapeRenderer();
    }

    /***
     * Method that will render the health bars for all the fortresses and engines in the game
     */
    public void renderBars(OrthographicCamera camera) {
        barRenderer.setProjectionMatrix(camera.combined);

        //Render the health bar for all entities in the game
        for(Engine e: engines){
            renderHealthBarForAttacker(e);
            renderWaterBarForEngine(e);
        }
        for(Fortress f: fortresses){
            renderHealthBarForAttacker(f);
        }
    }

    private void renderHealthBarForAttacker(Attacker a){
        barRenderer.begin(ShapeRenderer.ShapeType.Filled);

        barRenderer.setColor(169.0f/255.0f, 169.0f/255.0f, 169.0f/255.0f, 1);
        barRenderer.rect(a.getX() - 2, a.getY() + a.getHeight(), a.getWidth() + 4, 9);

        int healthBoundary1 = a.getMaxHealth() / 2;
        int healthBoundary2 = a.getMaxHealth() / 4;

        if(a.getHealth() >= healthBoundary1){
            barRenderer.setColor(Color.GREEN);
        }else if(a.getHealth() >= healthBoundary2){
            barRenderer.setColor(Color.YELLOW);
        }else{
            barRenderer.setColor(Color.RED);
        }

        float healthBarLength = ((float)a.getWidth() / (float)a.getMaxHealth()) * a.getHealth();
        barRenderer.rect(a.getX(), a.getY() + a.getHeight() + 2, healthBarLength, 5);

        barRenderer.end();
    }

    private void renderWaterBarForEngine(Engine e){
        barRenderer.begin(ShapeRenderer.ShapeType.Filled);

        barRenderer.setColor(169.0f/255.0f, 169.0f/255.0f, 169.0f/255.0f, 1);
        barRenderer.rect(e.getX() - 2, e.getY() - 9, e.getWidth() + 4, 9);

        int healthBoundary1 = e.getMaxVolume() / 2;
        int healthBoundary2 = e.getMaxVolume() / 4;

        barRenderer.setColor(0.0f, 167.0f/255.0f, 190.0f/255.0f, 1.0f);

        float waterBarLength = ((float)e.getWidth() / (float)e.getMaxVolume()) * e.getVolume();
        barRenderer.rect(e.getX(), e.getY() - 7, waterBarLength, 5);

        barRenderer.end();
    }
}
