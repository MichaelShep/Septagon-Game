package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.septagon.game.InputManager;

import java.awt.*;

/*
Child of the State class that will be used to manage the system when the user has reached game over
 */

public class GameOverState extends State
{
    private GlyphLayout gameOverLabel;
    private GlyphLayout extraInfoLabel;
    private GlyphLayout playAgainLabel;
    private GlyphLayout yesLabel;
    private GlyphLayout noLabel;

    private int gameOverX, gameOverY, extraInfoX, extraInfoY, playAgainX, playAgainY, yesX, yesY, noX, noY;

    private boolean didWin = false;
    private SpriteBatch gameOverSpriteBatch;

    private BitmapFont blueFont;

    private int position = 1;
    private Rectangle yesBox;
    private Rectangle noBox;

    public GameOverState(InputManager inputManager, BitmapFont font, StateManager stateManager, boolean didWin)
    {
        super(inputManager, font, StateID.GAME_OVER, stateManager);
        this.didWin = didWin;
    }

    public void initialise()
    {
        gameOverLabel = new GlyphLayout();
        extraInfoLabel = new GlyphLayout();
        playAgainLabel = new GlyphLayout();
        yesLabel = new GlyphLayout();
        noLabel = new GlyphLayout();

        gameOverSpriteBatch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GameFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.color = Color.BLUE;
        parameter.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!'()>?:-";
        blueFont = generator.generateFont(parameter);

        if(didWin){
            gameOverLabel.setText(font, "Congrats, you win!");
            extraInfoLabel.setText(font, "You destroyed all the ET Fortresses and saved York!");
        }else {
            gameOverLabel.setText(font, "Game Over! You Lose.");
            extraInfoLabel.setText(font, "All your fire engines were destroyed!");
        }
        playAgainLabel.setText(font, "Play Again?");
        yesLabel.setText(font, "Yes");
        noLabel.setText(font, "No");

        gameOverX = (int)((Gdx.graphics.getWidth() / 2) - gameOverLabel.width / 2);
        gameOverY = (int)((Gdx.graphics.getHeight() / 2) + gameOverLabel.height + 100);
        extraInfoX = (int)((Gdx.graphics.getWidth() / 2) - extraInfoLabel.width / 2);
        extraInfoY = (int)((Gdx.graphics.getHeight() / 2) + 70);
        playAgainX = (int)((Gdx.graphics.getWidth() / 2) - playAgainLabel.width / 2);
        playAgainY = (int)((Gdx.graphics.getHeight() / 2) + 20);
        yesX = (int)((Gdx.graphics.getWidth() / 2) - yesLabel.width / 2);
        yesY = (int)((Gdx.graphics.getHeight() / 2) - 60);
        noX = (int)((Gdx.graphics.getWidth() / 2) - noLabel.width / 2);
        noY = (int)((Gdx.graphics.getHeight() / 2) - 110);

        setupRectangles();
    }

    public void update()
    {
    }

    public void render(SpriteBatch batch)
    {
        Gdx.gl.glClearColor(50.0f/255.0f, 205.0f/255.0f, 50.0f/255.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameOverSpriteBatch.begin();

        font.setColor(Color.WHITE);

        if(didWin){
            gameOverLabel.setText(font, "Congrats, you win!");
            extraInfoLabel.setText(font, "You destroyed all the ET Fortresses and saved York!");
        }
        playAgainLabel.setText(font, "Play Again?");
        yesLabel.setText(font, "Yes");
        noLabel.setText(font, "No");

        font.draw(gameOverSpriteBatch, gameOverLabel, gameOverX, gameOverY);
        font.draw(gameOverSpriteBatch, extraInfoLabel, extraInfoX, extraInfoY);
        font.draw(gameOverSpriteBatch, playAgainLabel, playAgainX, playAgainY);
        this.setTextColour(1, yesLabel, "Yes");
        font.draw(gameOverSpriteBatch, yesLabel, yesX, yesY);
        this.setTextColour(2, noLabel, "No");
        font.draw(gameOverSpriteBatch, noLabel, noX, noY);

        gameOverSpriteBatch.end();
    }

    private void setTextColour(int currentPosition, GlyphLayout currentLabel, String text){
        if(currentPosition == this.position){
            font.setColor(Color.BLUE);
            currentLabel.setText(font, text);
        }
        else{
            font.setColor(Color.WHITE);
            currentLabel.setText(font, text);
        }
    }

    private void setupRectangles(){
        yesX = (int)((Gdx.graphics.getWidth() / 2) - yesLabel.width / 2);
        yesY = (int)((Gdx.graphics.getHeight() / 2) - 60);
        noX = (int)((Gdx.graphics.getWidth() / 2) - noLabel.width / 2);
        noY = (int)((Gdx.graphics.getHeight() / 2) - 110);

        yesBox = new Rectangle();
        noBox = new Rectangle();

        yesBox.setBounds(yesX - 20, yesY - 20, 100, 30);
        noBox.setBounds(noX - 20, noY - 20, 100, 30);
    }

    public void checkIfButtonPressed(float x, float y){
        if(x >= yesBox.x && x <= yesBox.x + yesBox.width && y >= yesBox.y && y <= yesBox.y + yesBox.height){
            System.out.println("YES BUTTON PRESSED");
        }else if(x >= noBox.x && x <= noBox.x + noBox.width && y >= noBox.y && y <= noBox.y + noBox.height){
            System.out.println("NO BUTTON PRESSED");
        }
    }

    public int getPosition(){ return position; }

    public void setPosition(int position) { this.position = position; }
}
