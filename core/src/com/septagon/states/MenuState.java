package com.septagon.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    private SpriteBatch menuBatch;
    private OrthographicCamera menuCamera;

    /***
     * Constructor that set initial values for all class member variables
     * @param inputManager The games InputManager class so that this class can also handle input
     * @param font The games font so that the class can draw text to the screen
     */
    public MenuState(InputManager inputManager, BitmapFont font, StateManager stateManager)
    {
        super(inputManager, font, StateID.MENU, stateManager);
        titleLabel = "Kroy - Septagon";
        playLabel = "Play";
        helpLabel = "Help";
        settingsLabel = "Settings";
        exitLabel = "Exit";
        menuPosition = 0;
        backgroundImage = null;
        layout = new GlyphLayout(font, titleLabel);
    }

    public void initialise()
    {
        menuBatch = new SpriteBatch();
        menuCamera = new OrthographicCamera();
        menuCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        menuCamera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        menuCamera.update();
        menuBatch.setProjectionMatrix(menuCamera.combined);
    }

    public void update()
    {
        menuBatch.setProjectionMatrix(menuCamera.combined);
    }

    public void render(SpriteBatch batch)
    {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuBatch.begin();

        font.setColor(Color.WHITE);
        titleCentreX = (int)(Gdx.graphics.getWidth() / 2 - layout.width / 2);
        font.draw(menuBatch, titleLabel, titleCentreX, (Gdx.graphics.getHeight()) - 30);

        drawString(menuBatch, 0, playLabel, 100, (Gdx.graphics.getHeight()) - 100);
        drawString(menuBatch, 1, helpLabel, 100, (Gdx.graphics.getHeight()) - 150);
        drawString(menuBatch, 2, settingsLabel, 100, (Gdx.graphics.getHeight()) - 200);
        drawString(menuBatch, 3, exitLabel,  100, (Gdx.graphics.getHeight()) - 250);

        menuBatch.end();
    }

    /***
     * Method used to draw each individual string to the screen, based on if it is the current position or not
     * @param batch The spritebatch that is used for rendering all objects
     * @param position The position in the list of menu items that the current string is
     * @param text The text that should be rendered
     * @param x The x position of the string on the screen
     * @param y The y position of the string on the screen
     */
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

    //Getters and Setters for member variable menuPosition
    public void setMenuPosition(int menuPosition)
    {
        if(menuPosition < 0 || menuPosition >= NUM_MENU_ITEMS)
            return;

        this.menuPosition = menuPosition;
    }

    public void hasResized(float width, float height){
        menuCamera.viewportWidth = width;
        menuCamera.viewportHeight = height;
        menuCamera.position.x = Gdx.graphics.getWidth() / 2;
        menuCamera.position.y = Gdx.graphics.getHeight() / 2;
        menuCamera.update();
    }

    public int getMenuPosition() { return menuPosition; }
    public OrthographicCamera getMenuCamera() { return menuCamera; }
}
