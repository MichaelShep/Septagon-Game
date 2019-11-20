package com.septagon.states;

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

    public MenuState()
    {
        titleLabel = "";
        playLabel = "";
        helpLabel = "";
        exitLabel = "";
        menuPosition = 0;
    }

    public void initialise()
    {

    }

    public void update()
    {

    }

    public void render()
    {

    }

    private void moveMenuPosition(char direction)
    {
    }
}
