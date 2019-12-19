package com.septagon.game;

/*
Class that is used for rendering and managing all the heads up display
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.septagon.entites.Engine;
import com.septagon.states.GameState;

public class UIManager
{
    private BitmapFont font;
    private BitmapFont smallFont;
    private ShapeRenderer engineStatsRenderer;
    private SpriteBatch uiBatch;

    private GlyphLayout playerTurnText;
    private GlyphLayout enemyTurnText;
    private GlyphLayout showEngineStatsText;

    private GameState gameState;

    private Engine currentEngine;

    private boolean displayingStats = false;

    private int showRectX, showRectY, showRectWidth, showRectHeight;

    public UIManager(BitmapFont font, GameState gameState)
    {
        this.font = font;
        this.gameState = gameState;
    }

    public void initialise()
    {
        engineStatsRenderer = new ShapeRenderer();
        uiBatch = new SpriteBatch();

        playerTurnText = new GlyphLayout(font, "Your Turn");
        enemyTurnText = new GlyphLayout(font, "Enemy Turn");
        showEngineStatsText = new GlyphLayout(font, "Show Engine Stats");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GameFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:-";
        smallFont = generator.generateFont(parameter);
        generator.dispose();

        showRectX = Gdx.graphics.getWidth() - 150;
        showRectY = Gdx.graphics.getHeight() - 60;
        showRectWidth = 100;
        showRectHeight = 30;
    }

    public void render()
    {
        uiBatch.begin();
        //Draws the button that allows the user to see stats about an engine
        if(currentEngine != null && !displayingStats)
        {
            engineStatsRenderer.setColor(Color.GRAY);
            engineStatsRenderer.rect(showRectX, showRectY, showRectWidth, showRectHeight);
        }
        engineStatsRenderer.end();

        engineStatsRenderer.begin(ShapeRenderer.ShapeType.Line);
        //Draws the button that allows the user to see stats about an engine
        if(currentEngine != null && !displayingStats)
        {
            engineStatsRenderer.setColor(Color.BLACK);
            engineStatsRenderer.rect(showRectX, showRectY, showRectWidth, showRectHeight);
        }
        engineStatsRenderer.end();
        uiBatch.end();

        uiBatch.begin();
        engineStatsRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //Draws the text that tells the player who's turn it is
        if (gameState.isPlayerTurn()){
            font.setColor(Color.BLUE);
            font.draw(uiBatch, playerTurnText, Gdx.graphics.getWidth()/2 - 30, Gdx.graphics.getHeight() - 32);
        }else{
            font.setColor(Color.RED);
            font.draw(uiBatch, enemyTurnText, Gdx.graphics.getWidth()/2 - 30, Gdx.graphics.getHeight() - 32);
        }

        if(currentEngine != null && !displayingStats) {
            smallFont.setColor(Color.BLACK);
            smallFont.draw(uiBatch, showEngineStatsText, showRectX + 10, showRectY - 10);
        }
        uiBatch.end();
    }

    public void dispose()
    {
        uiBatch.dispose();
        engineStatsRenderer.dispose();
    }

    public boolean isDisplayingStats()
    {
        return displayingStats;
    }

    public void setDisplayingStats(boolean stats)
    {
        this.displayingStats = displayingStats;
    }

    public void setCurrentEngine(Engine engine)
    {
        this.currentEngine = engine;
    }
}
