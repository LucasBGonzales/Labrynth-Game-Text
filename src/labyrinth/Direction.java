package labyrinth;

import game.Game;

/**
 * Direction enum. This handles identification of directions for exits and movement.
 * @author Lucas Gonzales
 * 
 * @version 23.11.2017
 * • Basic functionality done.
 * @version 27.11.2017
 * • Expanded from four points to eight points of the compass. (Northwest, Southeast...)
 * • Implement the eight points into the functions.
 */
public enum Direction {
    NORTH("North"), SOUTH("South"), EAST("East"), WEST("West"),
    NORTHWEST("Northwest"), NORTHEAST("Northeast"),
    SOUTHWEST("Southwest"), SOUTHEAST("Southeast");
    
    private String m_value;
    
    /**
     * Constructor
     * @param arg0 Enum value
     */
    Direction(String arg0)
    {
        m_value = arg0;
    }
    
    public static boolean isOrdinalDirection(Direction arg0)
    {
        if( arg0.equals(NORTHWEST) || arg0.equals(SOUTHWEST) ||
            arg0.equals(NORTHEAST) || arg0.equals(SOUTHEAST) )
        {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param arg0 First Location
     * @param arg1 Second Location
     * @return The nearest direction toward the angle between the two given points.
     */
    public static Direction getRelativeDirection(Location arg0, Location arg1)
    {
        double x, y;
        x = arg1.getX() - arg0.getX();
        y = arg1.getY() - arg0.getY();
        return getRelativeDirection((int) (Math.atan2(y,x) * 180 / Math.PI));
    }
    
    /**
     * @param arg0 Angle in degrees.
     * @return The nearest direction toward the given angle. 
     */
    public static Direction getRelativeDirection(int arg0)
    {
        if(arg0 < 0)
            arg0+=360;
        Game.logger.debug("Angle to Compare: " + arg0);
        /** OLD Four point compass
        if(arg0 >= 225 && arg0 <= 315)
            return NORTH;
        else if(arg0 >= 45 && arg0 <= 135)
            return SOUTH;
        else if(arg0 > 135 && arg0 < 225)
            return WEST;
        else
            return EAST;
        */
        if(arg0 >= 248 && arg0 <= 292)
            return NORTH;
        else if(arg0 >= 67 && arg0 <= 113)
            return SOUTH;
        else if(arg0 >= 157 && arg0 <= 203)
            return WEST;
        else if(arg0 >= 338 && arg0 <= 22)
            return EAST;
        else if(arg0 > 22 && arg0 < 67)
            return SOUTHEAST;
        else if(arg0 > 292 && arg0 < 338)
            return NORTHEAST;
        else if(arg0 > 203 && arg0 < 248)
            return NORTHWEST;
        else /*if(arg0 > 113 && arg0 < 157)*/
            return SOUTHWEST;
    }
    
    /**
     * @return Direction opposite of this one.
     */
    public Direction getOpposite()
    {
        if(NORTH.equals(m_value))
            return SOUTH;
        else if(SOUTH.equals(m_value))
            return NORTH;
        else if(WEST.equals(m_value))
            return EAST;
        else if(EAST.equals(m_value))
            return WEST;
        else if(SOUTHEAST.equals(m_value))
            return NORTHWEST;
        else if(SOUTHWEST.equals(m_value))
            return NORTHEAST;
        else if(NORTHEAST.equals(m_value))
            return SOUTHWEST;
        else
            return SOUTHEAST;
    }
    
    /**
     * @param arg0 Direction to go
     * @param arg1 Location to go from.
     * @return Returns the location in the given direction.
     */
    public static Location getLocation(Direction arg0, Location arg1)
    {
        Location loc = new Location(arg1);
        if(arg0 == NORTH || arg0 == NORTHWEST || arg0 == NORTHEAST)
            loc.setY(loc.getY()-1);
        if(arg0 == SOUTH || arg0 == SOUTHWEST || arg0 == SOUTHEAST)
            loc.setY(loc.getY()+1);
        if(arg0 == WEST || arg0 == NORTHWEST || arg0 == SOUTHWEST)
            loc.setX(loc.getX()-1);
        if(arg0 == EAST || arg0 == NORTHEAST || arg0 == SOUTHEAST)
            loc.setX(loc.getX()+1);
        return loc;
    }
    
    /**
     * @param arg0 String to test if it is representative of a valid Direction.
     * @return Is or Is not valid.
     */
    public static boolean isValid(String arg0)
    {
        Direction[] dirs = Direction.values();
        for(int i=0; i < dirs.length; i++)
            if(dirs[i].toString().toLowerCase().equals(arg0))
                return true;
        return false;
    }
    
    /**
     * @param arg0 String representative of a Direction
     * @return Direction corresponding to arg0. If no Direction exists, then null.
     */
    public static Direction getDirection(String arg0)
    {
        arg0 = arg0.toLowerCase();
        for(int i=0; i < Direction.values().length; i++)
            if(Direction.values()[i].value().equals(arg0))
                return Direction.values()[i];
        return null;
    }
    
    /**
     * @ return Returns the direction string with a capitalized first letter.
     */
    public String toString()
    { return m_value; }
    
    /**
     * @return Returns the direction string all lower case.
     */
    public String value()
    { return m_value.toLowerCase(); }
    
    /**
     * Tests for equivalence.
     * @param arg Direction to compare
     * @return True if same Direction, false if not.
     */
    public boolean equals(Direction arg)
    { return arg.toString().equals(this.toString()); }
    
    /**
     * Tests for equivalence to a String.
     * @param arg Direction to compare
     * @return True if same Direction, false if not.
     */
    public boolean equals(String arg)
    { return arg.equals(this.toString()); }
}
