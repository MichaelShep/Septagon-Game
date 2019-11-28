package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.septagon.game.InputManager;

/*
Child of the State class that will be used to manage the system when the user is in the menu
 */

public class MenuState extends State
{
    public static final int NUM_MENU_ITEMS = 4;

    private String titleLabel;
    private String playLabel;
    private String helpLabel;
    private String settingsLabel;
    private String exitLabel;
    private int menuPosition;

    private Texture backgroundImage;

    private GlyphLayout layout;
    private int titleCentreX;

    public MenuState(InputManager inputManager, BitmapFont font)
    {
        super(inputManager, font, StateID.MENU);
        titleLabel = "Kroy - Septagon";
        playLabel = "Play";
        helpLabel = "Help";
        settingsLabel = "Settings";
        exitLabel = "Exit";
        menuPosition = 0;
        backgroundImage = null;
        layout = new GlyphLayout(font, titleLabel);
        titleCentreX = (int)(Gdx.graphics.getWidth() / 2 - layout.width / 2) - (Gdx.graphics.getWidth() / 2);
    }

    public void initialise()
    {

    }

    public void update()
    {

    }

    public void render(SpriteBatch batch)
    {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        font.setColor(Color.WHITE);
        font.draw(batch, titleLabel, titleCentreX, (Gdx.graphics.getHeight() / 2) - 30);

        drawString(batch, 0, playLabel, -(Gdx.graphics.getWidth() / 2) + 100, (Gdx.graphics.getHeight() / 2) - 100);
        drawString(batch, 1, helpLabel, -(Gdx.graphics.getWidth() / 2) + 100, (Gdx.graphics.getHeight() / 2) - 150);
        drawString(batch, 2, settingsLabel, -(Gdx.graphics.getWidth() / 2) + 100, (Gdx.graphics.getHeight() / 2) - 200);
        drawString(batch, 3, exitLabel, -(Gdx.graphics.getWidth() / 2) + 100, (Gdx.graphics.getHeight() / 2) - 250);
    }

    private void drawString(SpriteBatch batch, int position, String text, float x, float y)
    {
        if(position == menuPosition)
        {
            font.setColor(Color.GREEN);
        }else
        {
            font.setColor(Color.WHITE);
        }

        font.draw(batch, text, x, y);
    }

    public void setMenuPosition(int menuPosition)
    {
        if(menuPosition < 0 || menuPosition >= NUM_MENU_ITEMS)
            return;

        this.menuPosition = menuPosition;
    }

    public int getMenuPosition() { return menuPosition; }
}
