package com.septagon.game;

/*
Class that is used for rendering and managing all the heads up display
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.septagon.entites.Engine;
import com.septagon.states.GameState;
import java.awt.*;

public class UIManager
{
    //Create variables for all the different font varients that will be used
    private BitmapFont blueFont;
    private BitmapFont redFont;
    private BitmapFont smallFont;
    private BitmapFont font;

    //Create the renderer for rendering all the gui shapes and the spritebatch for rendering all the gui
    private ShapeRenderer engineStatsRenderer;
    private SpriteBatch uiBatch;

    //Create objects for all the pieces of text that will be rendered to the screen
    private GlyphLayout playerTurnText;
    private GlyphLayout enemyTurnText;
    private GlyphLayout showEngineStatsText;
    private GlyphLayout maxVolumeText;
    private GlyphLayout healthText;
    private GlyphLayout damageText;
    private GlyphLayout rangeText;
    private GlyphLayout speedText;
    private GlyphLayout minimiseSymbol;

    //Creates objects for all pieces of text that will displayed when the game is paused
    private GlyphLayout pauseText;
    private int pauseTextX, pauseTextY;
    private GlyphLayout resumeText;
    private int resumeTextX, resumeTextY;
    private GlyphLayout exitText;
    private int exitTextX, exitTextY;

    //Create objects of the current instance of gamestate and for the currently pressed engine
    private GameState gameState;
    private Engine currentEngine;

    //Booolean that will tell if the stats menu is open or closed
    private boolean displayingStats = false;

    //Position variables for all gui elements
    private int showRectX, showRectY, showRectWidth, showRectHeight;
    private int statsRectX, statsRectY, statsRectWidth, statsRectHeight;
    private int minimiseX, minimiseY, minimiseWidth, minimiseHeight;
    private float playerTurnX, playerTurnY, enemyTurnX, enemyTurnY;

    private int pauseRectX, pauseRectY, pauseRectWidth, pauseRectHeight;
    private boolean paused = false;
    private int pauseMenuPosition = 1;

    public UIManager(GameState gameState, BitmapFont font)
    {
        this.gameState = gameState;
        this.font = font;
    }

    public void initialise()
    {
        engineStatsRenderer = new ShapeRenderer();
        uiBatch = new SpriteBatch();

        //Generate font from font file and create all the varients of it
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GameFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.color = Color.GREEN;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:-";
        smallFont = generator.generateFont(parameter);
        parameter.size = 32;
        parameter.color = Color.BLUE;
        blueFont = generator.generateFont(parameter);
        parameter.color = Color.RED;
        redFont = generator.generateFont(parameter);
        generator.dispose();

        //Just a temporary fix as cannot work out how to change colours properly yet
        playerTurnText = new GlyphLayout(blueFont, "Your Turn");
        enemyTurnText = new GlyphLayout(redFont, "Enemy Turn");

        //Set up all the text objects
        showEngineStatsText = new GlyphLayout(smallFont, "Show Stats");
        maxVolumeText = new GlyphLayout(smallFont, "Max Volume: 0");
        healthText = new GlyphLayout(smallFont, "Health: 0");
        damageText = new GlyphLayout(smallFont, "Damage: 0");
        rangeText = new GlyphLayout(smallFont, "Range: 0");
        speedText = new GlyphLayout(smallFont, "Speed: 0");
        minimiseSymbol = new GlyphLayout(smallFont, "-");

        font.setColor(Color.GREEN);
        pauseText = new GlyphLayout(font, "Paused");
        font.setColor(Color.WHITE);
        resumeText = new GlyphLayout(font, "Resume");
        exitText = new GlyphLayout(font, "Exit");

        setupPositions();
    }

    public void render()
    {
        uiBatch.begin();
        //Draws either the button to open the stats menu or the stats menu itself
        if(currentEngine != null && !displayingStats)
        {
            engineStatsRenderer.setColor(Color.GRAY);
            engineStatsRenderer.rect(showRectX, showRectY, showRectWidth, showRectHeight);
        }
        else if(currentEngine != null && displayingStats)
        {
            engineStatsRenderer.setColor(Color.GRAY);
            engineStatsRenderer.rect(statsRectX, statsRectY, statsRectWidth, statsRectHeight);
        }
        engineStatsRenderer.end();

        //Draws the outline for the button/menu
        engineStatsRenderer.begin(ShapeRenderer.ShapeType.Line);
        if(currentEngine != null && !displayingStats)
        {
            engineStatsRenderer.setColor(Color.BLACK);
            engineStatsRenderer.rect(showRectX, showRectY, showRectWidth, showRectHeight);
        }
        else if(currentEngine != null && displayingStats)
        {
            engineStatsRenderer.setColor(Color.BLACK);
            engineStatsRenderer.rect(statsRectX, statsRectY, statsRectWidth, statsRectHeight);
            engineStatsRenderer.rect(minimiseX, minimiseY, minimiseWidth, minimiseHeight);
        }
        engineStatsRenderer.end();
        uiBatch.end();

        //Draws all the text to the screen in its correct place
        uiBatch.begin();
        engineStatsRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //Draws the text that tells the player who's turn it is
        if (!gameState.isEnemyTurn()){
            blueFont.draw(uiBatch, playerTurnText, playerTurnX, playerTurnY);
        }else{
            redFont.draw(uiBatch, enemyTurnText, enemyTurnX, enemyTurnY);
        }

        if(paused){
            engineStatsRenderer.setColor(Color.LIGHT_GRAY);
            engineStatsRenderer.rect(pauseRectX, pauseRectY, pauseRectWidth, pauseRectHeight);
            font.draw(uiBatch, pauseText, pauseTextX, pauseTextY);
            drawPauseText(uiBatch, "Resume", resumeText, resumeTextX, resumeTextY, 1);
            drawPauseText(uiBatch, "Exit",exitText, exitTextX, exitTextY, 2);
            //font.draw(uiBatch, resumeText, resumeTextX, resumeTextY);
            //font.draw(uiBatch, exitText, exitTextX, exitTextY);
        }
        //If stats are not showing, just display button text
        if(currentEngine != null && !displayingStats) {
            smallFont.setColor(Color.WHITE);
            smallFont.draw(uiBatch, showEngineStatsText, showRectX + 5, showRectY + 20);
        }
        //If stats are showing, draw all the text relating to them
        else if(currentEngine != null && displayingStats)
        {
            smallFont.setColor(Color.WHITE);
            uiBatch.draw(currentEngine.getTexture(), statsRectX + 50, statsRectY + statsRectHeight - 70);
            smallFont.draw(uiBatch, maxVolumeText, statsRectX + 10, statsRectY + statsRectHeight - 90);
            smallFont.draw(uiBatch, healthText, statsRectX + 10, statsRectY + statsRectHeight - 120);
            smallFont.draw(uiBatch, damageText, statsRectX + 10, statsRectY + statsRectHeight - 150);
            smallFont.draw(uiBatch, rangeText, statsRectX + 10, statsRectY + statsRectHeight - 180);
            smallFont.draw(uiBatch, speedText, statsRectX + 10, statsRectY + statsRectHeight - 210);
            smallFont.draw(uiBatch, minimiseSymbol, minimiseX + 7, minimiseY + 15);
        }
        uiBatch.end();
    }

    private void drawPauseText(SpriteBatch batch, String text, GlyphLayout layout, int xPos, int yPos, int position){
        if(position == pauseMenuPosition){
            font.setColor(Color.BLUE);
            layout.setText(font, text.toString());
        } else {
            font.setColor(Color.WHITE);
            layout.setText(font, text.toString());
        }
        font.draw(batch, layout, xPos, yPos);
    }

    public void setupPositions()
    {
        //Set up all positions for gui objects
        playerTurnX = (int)(Gdx.graphics.getWidth() / 2 - playerTurnText.width / 2);
        playerTurnY = Gdx.graphics.getHeight() - 30;
        enemyTurnX = (int)(Gdx.graphics.getWidth() / 2 - enemyTurnText.width / 2);
        enemyTurnY = Gdx.graphics.getHeight() - 30;

        showRectX = Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 4);
        showRectY = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 8);
        showRectWidth = Gdx.graphics.getWidth() / 5;
        showRectHeight = Gdx.graphics.getHeight() / 16;

        statsRectX = Gdx.graphics.getWidth() - 200;
        statsRectY = 200;
        statsRectWidth = 180;
        statsRectHeight = 250;

        minimiseX = statsRectX + statsRectWidth - 20;
        minimiseY = statsRectY + statsRectHeight - 20;
        minimiseWidth = Gdx.graphics.getWidth() / 32;
        minimiseHeight = Gdx.graphics.getHeight() / 24;

        pauseRectWidth = Gdx.graphics.getWidth() / 4 ;
        pauseRectHeight = Gdx.graphics.getHeight() / 2 - 70;
        pauseRectX = Gdx.graphics.getWidth() / 2 - pauseRectWidth / 2;
        pauseRectY = Gdx.graphics.getHeight() / 2 - pauseRectHeight / 2;

        pauseTextX = (int)(pauseRectX + (pauseRectWidth / 2) - (pauseText.width / 2));
        resumeTextX = (int)(pauseRectX + (pauseRectWidth / 2) - (resumeText.width / 2));
        exitTextX = (int)(pauseRectX + (pauseRectWidth / 2) - (exitText.width / 2));

        pauseTextY = (int)(pauseRectY + pauseRectHeight) - 15;
        resumeTextY = (int)(pauseRectY + pauseRectHeight) - 75;
        exitTextY = (int)(pauseRectY + pauseRectHeight) - 125;

    }

    //Called by InputManager when the use presses the showStats button
    public void pressedShowStatsButton()
    {
        if(!displayingStats) displayingStats = true;
    }

    //Called by InputManager when the use presses the minimise button
    public void pressedMinimiseButton()
    {
        if(displayingStats) displayingStats = false;
    }

    //When game has ended, dispose of all objects
    public void dispose()
    {
        uiBatch.dispose();
        engineStatsRenderer.dispose();
    }

    //Getters and Setters
    public boolean isDisplayingStats()
    {
        return displayingStats;
    }
    public boolean isPaused() { return paused; }

    public int getPauseMenuPosition() { return pauseMenuPosition; }

    public void setDisplayingStats(boolean stats)
    {
        this.displayingStats = displayingStats;
    }

    public void setPauseMenuPosition(int pauseMenuPosition){
        this.pauseMenuPosition = pauseMenuPosition;
    }

    public void setPaused(boolean paused) { this.paused = paused; }

    public void setCurrentEngine(Engine engine)
    {
        this.currentEngine = engine;
        maxVolumeText = new GlyphLayout(smallFont, "Max Volume: " + engine.getMaxVolume());
        healthText = new GlyphLayout(smallFont, "Health: " + engine.getHealth());
        damageText = new GlyphLayout(smallFont, "Damage: " + engine.getDamage());
        rangeText = new GlyphLayout(smallFont, "Range: " + engine.getRange());
        speedText = new GlyphLayout(smallFont, "Speed: " + engine.getSpeed());
    }

    public Rectangle getShowStatsRect()
    {
        return new Rectangle(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 4), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 8),
                Gdx.graphics.getWidth() / 5, Gdx.graphics.getHeight() / 16);
    }

    public Rectangle getMinimiseRect()
    {
        return new Rectangle(getShowStatsRect().x + getShowStatsRect().width - 20,
                getShowStatsRect().y + getShowStatsRect().height - 20,
                Gdx.graphics.getWidth() / 32, Gdx.graphics.getHeight() / 24);
    }
}