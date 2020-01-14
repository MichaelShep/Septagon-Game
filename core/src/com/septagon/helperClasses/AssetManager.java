package com.septagon.helperClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AssetManager {

    private static Texture engineTexture1 = new Texture(Gdx.files.internal("images/engine1.png"));
    private static Texture engineTexture2 = new Texture(Gdx.files.internal("images/engine2.png"));
    private static Texture moveSpaceTexture = new Texture(Gdx.files.internal("move_square.png"));

    private static Texture fortressFireTexture = new Texture(Gdx.files.internal("images/FortressFire.png"));
    private static Texture fortressMinisterTexture = new Texture(Gdx.files.internal("images/FortressMinister.png"));
    private static Texture fortressStationTexture = new Texture(Gdx.files.internal("images/FortressStation.png"));

    private static Texture fireStationTexture = new Texture(Gdx.files.internal("images/fireStation.png"));

    private static Texture fortressBoundaryImage = new Texture(Gdx.files.internal("selected fortress.png"));

    public static Texture getEngineTexture1() { return engineTexture1; }
    public static Texture getEngineTexture2() { return engineTexture2; }
    public static Texture getMoveSpaceTexture() { return moveSpaceTexture; }
    public static Texture getFortressFireTexture() { return fortressFireTexture; }
    public static Texture getFortressStationTexture() { return fortressStationTexture; }
    public static Texture getFortressMinisterTexture() { return fortressMinisterTexture; }
    public static Texture getFireStationTexture() { return fireStationTexture; }
    public static Texture getFortressBoundaryImage() { return fortressBoundaryImage; }
}
