package com.septagon.entites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap 
{
	//LibGDX variables that will be used to create and render the tile map
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;

	/***
	 * Constructor that initialises variables and uses libGDX to load the map of the game
	 */
	public TiledGameMap()
	{
		tiledMap = new TmxMapLoader().load("FirstMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}

	/***
	 * Render method to draw all the tiles to the screen
	 * @param camera The games camera
	 */
	public void render(OrthographicCamera camera)
	{
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}

	/***
	 * Disposes of all objects once the game is finished
	 */
	public void dispose()
	{
		tiledMap.dispose();
	}
	
	/***
	 * Will return the tile at a given map position
	 * @param layer - The map layer that you want to check for a tile on
	 * @param col - The column position of the tile
	 * @param row - the row position of the tile
	 * @return - returns the tile that it is at the location requested
	 */
	public Tile getTileByCoordinate(int layer, int col, int row)
	{
		//Gets the cell (format used by Tiled) at the current location
		Cell cell = ((TiledMapTileLayer)tiledMap.getLayers().get(layer)).getCell(col, row);
		
		//null checks to check both the cell and the tile actually exit
		if(cell != null)
		{
			TiledMapTile tile = cell.getTile();
			
			if(tile != null)
			{
				//Gets the id of the tile at the location and then returns a copy of that tile
				int id = tile.getId();
				return new Tile(col, row, null, false);
			}
		}
		//If either the cell or the tile doesn't exist, return null
		return null;
	}

	//Getters
	public int getMapWidth()
	{
		return ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getWidth();
	}
	
	public int getMapHeight()
	{
		return ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getHeight();
	}
}
