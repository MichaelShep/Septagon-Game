package com.septagon.entites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap 
{
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	
	public TiledGameMap()
	{
		tiledMap = new TmxMapLoader().load("FirstMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
	public void initialise()
	{
		System.out.println("MapWidth: " + this.getMapWidth());
		System.out.println("MapHeight: " + this.getMapHeight());
	}
	
	//Updates all the tiles on the screen
	public void update()
	{
	}
	
	//Draws the map to the screen based on the location of the game camera
	public void render(OrthographicCamera camera)
	{
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}
	
	//Disposes of all objects once the map is closed
	public void dispose()
	{
		tiledMap.dispose();
	}
	
	public Tile getTileByLocation(int layer, int x, int y)
	{
		//converts the screen coordinates into map coordinates to find the tile
		return this.getTileByCoordinate(layer, (int)(x / Tile.TILE_SIZE),
				(int)(y / Tile.TILE_SIZE));
	}
	
	/***
	 * Will return the tile at a given coordinate - works out what the tileType should be
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
				return new Tile(col * Tile.TILE_SIZE, row * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE,
						null, 'U', false, false, TileType.getTileById(id));

			}
		}
		//If either the cell or the tile doesn't exist, return null
		return null;
	}
	
	public int getMapWidth()
	{
		return ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getWidth();
	}
	
	public int getMapHeight()
	{
		return ((TiledMapTileLayer)tiledMap.getLayers().get(0)).getHeight();
	}
}
