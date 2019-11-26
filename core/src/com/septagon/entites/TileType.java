package com.septagon.entites;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/*
 * Enum class that is used to keep track of all the different types of
 * tile in the game
 */

public enum TileType
{	
	//Define different tiletypes using the enum definition
	//These are just tests that are used with the test map -
	//Will be edited for our final game when the different
	//types of tile are decided upon
	GRASS(1, "Grass"),
	DIRT(2, "Dirt"),
	SKY(3, "Sky"),
	LAVA(4, "Lava"),
	CLOUD(5, "Cloud"),
	STONE(6, "Stone");
	
	public static final int TILE_IMAGE_SIZE = 16;
	
	private int id;
	private String name;
	
	private TileType(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	//Getters for all member variables
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	private static HashMap<Integer, TileType> tileMap;
	
	//Sets up the hash map containing ids that link with the tile types and also the image for all tiles
	//MUST BE CALLED BEFORE TILEMAP CAN BE USED
	public static void setupTileTypeMap()
	{
		tileMap = new HashMap<Integer, TileType>();
		
		for(TileType tileType: TileType.values())
		{
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileById(int id)
	{
		return tileMap.get(id);
	}
}
