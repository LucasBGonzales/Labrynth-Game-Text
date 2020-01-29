package utility;

import labyrinth.Direction;
import labyrinth.Room;

/**
 * Mapper class takes a two-dimensional room array and creates a map representative of that
 * array and returns it as a string. The mapper takes into account rooms being known and unknown to
 * the player.
 * @author Lucas Gonzales
 * @version 4.12.2017
 * • Created the class.
 * • Created and completed getMapString method.
 */
public class Mapper {
	private static final char m_wallSymbol = '|';
	private static final char m_pathSymbol = 'x';
	
	/**
	 * @param arg0 The Room[][] rooms to map.
	 * @return A String representative of the map passed as arg0. 
	 */
	public static String getMapString(Room[][] arg0)
	{
		String map="";
		//For printing within bounds of known map.
		int strX=10000, strY=10000, strXf=0, strYf=0;
		/*
		|||||||
		|xxx|x|
		|x|x|x|
		|x|xxx|
		|||x|x|
		|xxx|x|
		|||||||
		 */
		//Every "exit" needs to be a path. Every "blocked exit" needs to be
		//a wall. This makes the size of the map the size of the array of rooms * 2 + 1 
		char[][] chrMap = new char[arg0.length*2+1][arg0[0].length*2+1];
		
		//Fill with walls.
		for(int x=0; x < chrMap.length; x++)
			for(int y=0; y < chrMap[x].length; y++)
				chrMap[x][y] = m_wallSymbol;

		//Fill appropriate paths.
		for(int x=0; x < arg0.length; x++)
			for(int y=0; y < arg0[x].length; y++)
			{
				//Map coordinates, accounting for walls and paths.
				int mapX = x*2+1;
				int mapY = y*2+1;
				if(arg0[x][y].isKnown())
				{
					//Update known bounds
					if(strX > mapX)
						strX = mapX;
					if(strY > mapY)
						strY = mapY;
					if(strXf < mapX)
						strXf = mapX;
					if(strYf < mapY)
						strYf = mapY;
					
					//Set to path
					chrMap[mapX][mapY] = m_pathSymbol;
					//Check surroundings for paths.
					//north
					if(arg0[x][y].hasExit(Direction.NORTH) && y > 0)
						if(arg0[x][y-1].isKnown())
								chrMap[mapX][mapY-1] = m_pathSymbol;
					//south
					if(arg0[x][y].hasExit(Direction.SOUTH) && y < arg0[x].length-1)
						if(arg0[x][y+1].isKnown())
								chrMap[mapX][mapY+1] = m_pathSymbol;
					//east
					if(arg0[x][y].hasExit(Direction.EAST) && x < arg0.length-1)
						if(arg0[x+1][y].isKnown())
								chrMap[mapX+1][mapY] = m_pathSymbol;
					//west
					if(arg0[x][y].hasExit(Direction.WEST) && x > 0)
						if(arg0[x-1][y].isKnown())
								chrMap[mapX-1][mapY] = m_pathSymbol;
				}
				
			}
		
		//Adjust bounds to include exterior walls.
		strY--;
		strX--;
		strYf+=2;
		strXf+=2;
		//Put into string.
		for(int y = strY; y < chrMap.length && y < strYf; y++)
		{
			for(int x = strX; x < chrMap[y].length && x < strXf; x++)
			{
				map+=chrMap[x][y];
			}
			map+="\n";
		}
		
		//Return Map String
		return map;
	}
}