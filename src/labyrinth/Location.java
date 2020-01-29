package labyrinth;

public class Location {
	private int xPos, yPos;
	
	public Location(Location loc)
	{
		this(loc.getX(),loc.getY());
	}
	
	public Location(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void setX(int xPos)
	{
		this.xPos = xPos;
	}
	
	public void setY(int yPos)
	{
		this.yPos = yPos;
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public String toString()
	{
		return xPos + ", " + yPos;
	}
	
	public boolean equals(int x, int y)
	{
		return x == getX() && y == getY();
	}
	
	public boolean equals(Location loc)
	{
		return loc.getX() == getX() && loc.getY() == getY();
	}
}
