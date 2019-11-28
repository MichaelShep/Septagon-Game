package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.septagon.game.InputManager;

/*
Child of the State class that will be used to manage the system when the user is in the menu
 */

public class MenuState extends State
{
    private String titleLabel;
    private String playLabel;
    private String helpLabel;
    private String exitLabel;
    private int menuPosition;

    public MenuState(InputManager inputManager, BitmapFont font)
    {
        super(inputManager, font);
        titleLabel = "Kroy - Septagon";
        playLabel = "Play";
        helpLabel = "Help";
        exitLabel = "Exit";
        menuPosition = 0;
    }

    public void initialise()
    {

    }

    public void update()
    {

    }

    public void render(SpriteBatch batch)
    {
        System.out.println("Running");
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        font.setColor(Color.BLACK);
        font.draw(batch, titleLabel, -20, 0);
    }

    private void moveMenuPosition(char direction)
    {
    }
}
