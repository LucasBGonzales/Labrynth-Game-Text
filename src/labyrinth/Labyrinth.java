package labyrinth;

import java.util.ArrayList;

import game.Game;
import items.Canteen;
import items.HealthPotion;
import items.Item;
import items.RedApple;
import items.RottenApple;
import utility.Mapper;

/**
 * Labyrinth class. This class contains all the rooms for the labyrinth, and 
 * appropriate methods with which to access them.
 * @author Lucas Gonzales
 * 
 * @version 26.11.2017
 * • Modified gerateLabyrinth to account for new method of generating descriptions.
 * 
 * @version 27.11.2017
 * • Added getDistance and getRelativeDistance.
 * • Modified getValidSealedExits to remove ordinal directions.
 *      • [NOTE] This can be removed later if I want to use cardinal and ordinal directions for up to
 *      eight exits.
 *      
 * @version 10.12.2017
 * • Added rudimentary item randomization to generateLabyrinth.
 */
public class Labyrinth {
    private static final int MINIMUM_SIZE = 7;
    private static Room[][] m_labyrinth;
    private double m_itemChance;
    
    /**
     * Default constructor.
     */
    public Labyrinth()
    { this(MINIMUM_SIZE, 0.5); }
    
    /**
     * @param size Size of the labyrinth sides
     */
    public Labyrinth(int size, double itemChance)
    {
        //Check minimum size
        if(size < MINIMUM_SIZE)
            size = MINIMUM_SIZE;
        
        m_itemChance = itemChance;
        
        //Check for odd size
        //Labyrinth must have odd sized edges.
        if((size % 2) == 0)
            size++;
        m_labyrinth = new Room[size][size];
        generateLabyrinth();
    }
    
    /**
     * @return Returns the side lengths of the labyrinth.
     */
    public int getSize()
    { return m_labyrinth.length; }
    
    /**
     * @param arg0 Location to get Room from
     * @return Returns the room at arg0.
     */
    public Room getRoom(Location arg0)
    { return m_labyrinth[arg0.getX()][arg0.getY()]; }
    
    /**
     * @return Returns a String representative of this labyrinth.
     */
    public String getMapString()
    { return Mapper.getMapString(m_labyrinth); }
    
        /**
         * Returns a double representative of how many Rooms apart two locations are from each other.
         * @param arg0 First Location
         * @param arg1 Second Location
         * @return Distance apart arg0 and arg1 are as a double.
         */
        public static double getDistance(Location arg0, Location arg1)
        {
            int x0,x1,y0,y1;
            x0 = arg0.getX();
            x1 = arg1.getX();
            y0 = arg0.getY();
            y1 = arg1.getY();
            // result = sqrt(yd^2 + xd^2). Pythagorean Thearum
            return Math.sqrt( Math.pow(y1 - y0,2) + Math.pow(x1 - x0,2) ); 
        }
        
        /**
         * Returns a double representative of how far across the Labyrinth
         * two Locations are from each other. This is represented as a percentage where return 1.0 is 100%.
         * @param arg0 First Location
         * @param arg1 Second Location
         * @return Relative distance apart arg0 and arg1 are as a double percentage.
         */
        public static double getRelativeDistance(Location arg0, Location arg1)
        {
            double distance = getDistance(arg0,arg1);
            return distance / m_labyrinth.length;
        }
    
    /**
     * Method to generate the Rooms in the labyrinth, and then set up the
     * exits to make it a maze.
     */
    private void generateLabyrinth()
    {
        //Initialize Rooms
        int baseDescriptorSize = 5;
        int descriptorDeviation = 2;
        utility.Descriptor describe = new utility.Descriptor();
        for(int row=0; row < getSize(); row++)
            for(int col=0; col < getSize(); col++)
            {
                /** OLD METHOD
                Room newRoom = new Room();
                //Base random generation
                int randNum = (int) (Math.random() * baseDescriptorSize) + 1;
                //Devation random generation
                int randDev = (int) ((Math.random() * (descriptorDeviation * 2)) - descriptorDeviation)+1;
                randNum += randDev;
                //Generate Room Descriptors
                for(int i=0; i < randNum; i++)
                {
                    String str = describe.getRandomDescriptor();
                    if(!newRoom.hasDescriptor(str))
                        newRoom.addDescriptor(str);
                }
                //Add room to labyrinth
                m_labyrinth[row][col] = newRoom;
                **/
                Room newRoom = new Room();
                newRoom.addDescription(describe.generateDescription(baseDescriptorSize, descriptorDeviation));
                // Add items randomly. Temporary implementation.
                double dropChance = Math.random();
                if(dropChance < m_itemChance)
                {
                	Item itemToAdd = null;
                	int numberOfItems = 4;
                	int indexToAdd = (int) (Math.random() * numberOfItems);
                	if(indexToAdd == 0)
                		itemToAdd = new HealthPotion();
                	if(indexToAdd == 1)
                	{
                		float drinkAmount = (float) (Math.random() * 100);
                		boolean quality = Math.random() < 0.5;
                		itemToAdd = new Canteen(drinkAmount, quality);
                	}
                	if(indexToAdd == 2)
                		itemToAdd = new RedApple();
                	if(indexToAdd == 3)
                		itemToAdd = new RottenApple();
                	newRoom.items().addItem(itemToAdd);
                }
                
                m_labyrinth[row][col] = newRoom;
            }
        
        
        //Build Maze
        ArrayList<Location> openList = new ArrayList<Location>();
        Location currentLoc, newLoc;
        //Pick starting location. Put it into open list.
        currentLoc = new Location(getSize()/2,getSize()/2);
        openList.add(currentLoc);
        //While there are locations in the open list
        while(openList.size() > 0)
        {
            int randOpen, randExit;
            
            //Randomly pick a location
            randOpen = (int)(Math.random() * openList.size());
            currentLoc = openList.get(randOpen);
            
            //Pick an adjoining wall and open it.
            ArrayList<Direction> sealedExits = getValidSealedExits(currentLoc);
            if(sealedExits.size() > 0)
            {
                randExit = (int)(Math.random() * sealedExits.size());
                //Open it
                getRoom(currentLoc).addExit(sealedExits.get(randExit));
            
                //Put new open location into open list if it has any valid walls.
                newLoc = Direction.getLocation(sealedExits.get(randExit), currentLoc);
                //Open it
                getRoom(newLoc).addExit(sealedExits.get(randExit).getOpposite());
                if(getValidSealedExits(newLoc).size() > 0)
                    openList.add(newLoc);
            }
            
            //If location has no valid walls, remove it from open list.
            if(getValidSealedExits(currentLoc).size() == 0)
                openList.remove(randOpen);
        }
        Game.logger.info("Done Generating Labyrinth");
    }
    
    /**
     * Gets valid sealed exits. Valid is defined as not leading to a valid location
     * relative to m_labyrinth and not being sealed on each side.
     * @param arg0 Location to of room to look at.
     * @return ArrayList<Direction> valid sealed exits.
     */
    private ArrayList<Direction> getValidSealedExits(Location arg0)
    {
        ArrayList<Direction> sealedExits = getRoom(arg0).getSealedExits();
        //Remove NW,NE,SW,SE
        for(int i=0; i < sealedExits.size(); i++)
        {
            if(Direction.isOrdinalDirection(sealedExits.get(i)))
            {
                sealedExits.remove(i);
                i--;
            }
        }
        //Remove invalids.
        for(int i=0; i < sealedExits.size(); i++)
        {
            Location loc = Direction.getLocation(sealedExits.get(i), arg0);
            if(!isValid(loc) || getRoom(loc).isClosedOff() == false)
            {
                sealedExits.remove(i);
                i--;
            }
        }
        
        return sealedExits;
    }
    
    /**
     * Set item spawn chance.
     * @param arg0 Value between 0 and 1.
     */
    public void setItemChance(double arg0)
    {
    	if(arg0 > 1)
    		arg0 = 1;
    	m_itemChance = arg0; 
    }
    
    /**
     * Prints out all location data for the labyrinth.
     */
    public void printMap()
    {
        for(int x=0; x < getSize(); x++)
        {
            for(int y=0; y < getSize(); y++)
            {
                Location loc = new Location(x,y);
                System.out.println("Location: " + loc.toString() + "\n" + m_labyrinth[x][y].exitString());
            }
            System.out.println();
        }
    }
    
    /**
     * Checks whether the location is within the bounds of m_labyrinth.
     * @param loc Location to examine.
     * @return True if valid, false if not.
     */
    private boolean isValid(Location loc)
    {
        int y = loc.getY();
        int x = loc.getX();
        return (y >= 0 && y < getSize()) && (x >= 0 && x < getSize());
    }
}
