package game;

import java.util.ArrayList;
import java.util.Scanner;
import entities.Player;
import items.Canteen;
import items.Consumable;
import items.Item;
import labyrinth.Direction;
import labyrinth.Labyrinth;
import labyrinth.Location;
import labyrinth.Room;
import utility.Log;

/**
 * NOTE TO SELF
 * Add dynamic item implementation. This might help:
 * https://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor
 * 
 * Labyrinth Game!
 * An adventure/maze game where the player is placed in the center of a labyrinthine maze and left to find their
 * way out. This is a turn based game with completely random paths and descriptions of rooms.
 * @author Lucas Gonzales
 * 
 * @version 23.11.2017
 * • Basic game is done.
 * 
 * @version 27.11.2017
 * • Added newlines in some dialogue to make it present better in BlueJ.
 * • Modified feelDirection to give the user some idea of their distance to the exit.
 * 
 * @version 28.11.2017
 * • Fixed help command to include "go back".
 * 
 * @version 3.12.2017
 * • Added checkMap method
 * • Added check map command.
 * • Made the game automatically execute the look command when entering a new room.
 * • Implemented Player entity. Only uses location right now.
 * 
 * @version 10.12.2017
 * • Added commands COM_CHECK_ITEMS, COM_CHECK_STATS, COM_GET, COM_GET_ITEM, COM_CONSUME
 *   and added functionality for each command.
 */
public class Game {
    private static final String COM_BACK = "back";
    private static final String COM_CHECK = "check";
    private static final String COM_CHECK_ITEMS = "inventory";
    private static final String COM_CHECK_MAP = "map";
    private static final String COM_CHECK_STATS = "stats";
    private static final String COM_CONSUME = "consume";
    private static final String COM_GET = "get";
    private static final String COM_GET_ITEM = "item";
    private static final String COM_GO = "go";
    private static final String COM_HELP = "help";
    private static final String COM_LOOK = "look";
    private static final String COM_LOOK_EXITS = "exits";
    private static final String COM_SURRENDER = "surrender";
    //private static Location m_playerLoc;
    private static Location m_destination;
    private static int m_turnCounter;
    private static boolean m_gameEnd;
    private static Labyrinth m_labyrinth;
    private static ArrayList<Direction> m_previousDirections;
    private static Player m_player;
    //Logger
    public static final Log logger = new Log();
    
    /**
     * Constructor for Game
     * @param arg0 Logger level.
     * @param arg1 Debug on/off
     */
    public Game(int arg0, boolean arg1)
    {
        this(arg0,arg1,0);
    }

    /**
     * Constructor for Game
     * @param arg0 Logger level.
     * @param arg1 Debug on/off
     * @param arg2 Size of Labyrinth.
     */
    public Game(int arg0, boolean arg1, int arg2)
    {
        //Initialize
    	m_previousDirections = new ArrayList<Direction>();
        m_turnCounter = 0;
        m_gameEnd=false;
        logger.setLevel(arg0);
        logger.setDebug(arg1);
        m_labyrinth = new Labyrinth(arg2, 0.8);
        Game.logger.debug(RegisteredItems.getClassesString());
        //Set up player location and destination.
        //m_playerLoc = new Location(m_labyrinth.getSize() / 2, m_labyrinth.getSize() / 2);
    	m_player = new Player(new Location(m_labyrinth.getSize() / 2, m_labyrinth.getSize() / 2));
    	m_player.setHealth(100f);
    	m_player.setHunger(100f);
    	m_player.setThirst(100f);
    }

	/**
     * Main play method for the game.
     */
    public void play()
    {
        //Randomly pick a destination that isn't equal to m_playerLoc
        int x, y;
        do
        {
            x = (int) (Math.random() * m_labyrinth.getSize());
            y = (int) (Math.random() * m_labyrinth.getSize());
            m_destination = new Location(x,y);
        }while(m_destination.equals(m_player.getLocation()));
        logger.debug("Player Location: " + m_player.getLocation().toString());
        logger.debug("Destination: " + m_destination.toString());
        
        //Add player starting items.
        m_player.items().addItem(new items.RedApple());
        m_player.items().addItem(new items.Canteen(50f, items.Canteen.CLEAN));

        //Introduce player to Labyrinth.
        logger.println("Welcome to the Labyrinth!");
        logger.println("Your job is to explore the Labyrinth and find the exit.");
        logger.println("Every few turns or so you will be informed of the general direction of the exit.");
        logger.println("Your turns are being counted, and when you either reach the end or give up,\n"
                       + "you will be informed of how many steps you took.");
        logger.println("Each room is quite unique, so pay attention. Try and remember landmarks.");
        logger.println("At any time you can type 'help' to get a full list of valid commands.");
        logger.println("Good Luck!\n");
        //Describe the room
        describeRoom();

        //Start game loop.
        Scanner s = new Scanner(System.in);
        String nextLine;
        while(!m_gameEnd)
        {
        	//Make sure current room is known
        	m_labyrinth.getRoom(m_player.getLocation()).isKnown(true);
        	
            //Tell the user which direction the exit is every few turns.
            if(m_turnCounter % 3 == 0)
                feelDirection();
            
            //Get User Input and process it
            nextLine = s.nextLine();
            processCommands(nextLine.split(" "));
            
            //Check if user has reached the destination.
            if(m_player.getLocation().equals(m_destination))
                playerWins();
        }
        //Stop the program from quitting immediately
        //Allow user time to read end-game messages.
        logger.println("Press Enter to Continue");
        s.nextLine();
        s.close();
    }
    
    /**
     * This method is run when victory conditions for the player are met.
     * Congrats the player and provides endgame data, and sets flag to end game loop.
     */
    private void playerWins()
    {
        logger.println("Congratulations! You found your way out!");
        logger.println("You took " + m_turnCounter + " turns to find your way out.");
        checkMap();
        m_gameEnd=true;
    }
    
    /**
     * Consolidates all commands into a single string, starting at a specified index.
     * Each command string is separated by a " " space.
     * @param arg0 Command list.
     * @param arg1 Index to start consolidating from.
     * @return Consolidated command string.
     */
    private String consolidateCommand(String[] arg0, int arg1)
    {
		String item="";
		for(int i=arg1; i < arg0.length; i++)
		{
			item += arg0[i];
			if(i != arg0.length-1)
				item += " ";
		}
		return item;
    }
    
    /**
     * Process the commands entered by the user.
     * @param arg0 The String array of commands.
     */
    private void processCommands(String[] arg0)
    {
        String commands="";
        for(int i=0; i < arg0.length; i++)
        {
            //Help with processing.
            arg0[i] = arg0[i].toLowerCase();
            //Debug variable.
            commands+=arg0[i] + " ";
        }
        logger.debug("Commands Entered: " + commands);
        
        //Check for commands greater than 2 in length.
        if(arg0.length > 2)
        {
        	if(arg0[0].equals(COM_GET))
        	{
        		if(arg0[1].equals(COM_GET_ITEM))
        		{
        			String item = consolidateCommand(arg0, 2);
        			getItem(item);
                	return;
        		}
        	}
        }
        if(arg0.length >= 2)
        {
            if(arg0[0].equals(COM_GO))
            {
                if(arg0[1].equals(COM_BACK))
                {
                	goBack();
                	return;
                }
                else if(Direction.isValid(arg0[1]))
                {
                	goRoom(arg0[1], false);
                	return;
                }
                else
                {
                    logger.println("Invalid Direction.\n");
                    return;
                }
            }
            else if(arg0[0].equals(COM_CONSUME))
            {
    			String item = consolidateCommand(arg0, 1);
        		consume(item);
            	return;
            }
            else if(arg0[0].equals(COM_LOOK))
            {
                if(arg0[1].equals(COM_LOOK_EXITS))
                {
                	describeExits();
                	return;
                }
                else
                {
                    logger.println("Invalid Look Command.");
                    return;
                }
            }
            else if(arg0[0].equals(COM_CHECK))
            {
            	if(arg0[1].equals(COM_CHECK_MAP))
            	{
            		checkMap();
                	return;
            	}
            	else if(arg0[1].equals(COM_CHECK_ITEMS))
            	{
            		checkItems();
                	return;
            	}
            	else if(arg0[1].equals(COM_CHECK_STATS))
            	{
            		checkStats();
                	return;
            	}
                else
                {
                    logger.println("Invalid Check Command.");
                    return;
                }
        	}
            else if(arg0[0].equals(COM_GET))
        	{
    			String item = consolidateCommand(arg0, 1);
    			getItem(item);
            	return;
    		}
        }if(arg0.length == 1)
        {
            if(arg0[0].equals(COM_HELP))
            {
            	help();
            	return;
            }
            else if(arg0[0].equals(COM_SURRENDER))
            {
            	surrender();
            	return;
            }
            else if(arg0[0].equals(COM_LOOK))
            {
            	describeRoom();
            	return;
            }
        }
        
        //Command was never executed, and therefore wasn't recognized.
        logger.println("Command not Recognized.");
        return;
        
    }
    
    /**
     * For command "get item <itemname>"
     * Will find the item in the room, if it exists, and add it to the
     * player inventory and remove it from the room. 
     * @param arg0 Item to get.
     */
    private void getItem(String arg0)
    {
    	Room currentRoom = m_labyrinth.getRoom(m_player.getLocation());
    	Item item = currentRoom.items().retrieveItem(arg0);
    	if(item != null)
    	{
    		m_player.items().addItem(item);
    		logger.println("You retrieved the " + item.getName() + " and added "
    				+ "it to your inventory.");
    	}else
    		logger.println("That item isn't here.");
    }
    
    /**
     * Consume a consumable item.
     * TODO Multiple Canteens are not handled at all. Fix this.
     * @param arg0 Item to consume
     */
    private void consume(String arg0)
    {
    	Item item = m_player.items().retrieveItem(arg0);
    	
    	//Check if item was retrieved.
    	if(item == null)
    	{
    		logger.println("That item is not in your inventory.");
    		return;
    	}
    	//Check if item is consumable.
    	if(item instanceof Consumable == false)
    	{
    		logger.println("That item is not consumable.");
    		return;
    	}
    	
    	//Consume.
    	Consumable consumableItem = (Consumable) item;
    	String description = consumableItem.consume(m_player);
    	logger.println(description);
    	
    	//Check for Canteen. If so, then put it back in player inventory.
    	if(item instanceof Canteen)
    	{
    		m_player.items().addItem(item);
    	}
    }
    
    private void checkMap()
    {
    	logger.println(m_labyrinth.getMapString() + "\n");
    }
    
    private void checkItems()
    {
    	if(m_player.items().getList().size() < 1)
    	{
    		logger.println("You have no items in your inventory.");
    		return;
    	}
    	logger.print("You have ");
    	logger.println(m_player.items().listItems());
    }
    
    private void checkStats()
    {
    	logger.println(m_player.getHunger().describeValue());
    	logger.println(m_player.getThirst().describeValue());
    	logger.println(m_player.getHealth().describeValue());
    }
    
    private void goBack()
    {
    	if(m_previousDirections.size() > 0)
    	{
	    	int index = m_previousDirections.size() - 1;
	    	Direction dirToGo = m_previousDirections.get(index);
	    	m_previousDirections.remove(index);
	    	//Turn around
	    	dirToGo = dirToGo.getOpposite();
	    	goRoom(dirToGo.toString(), true);
    	}else
    		logger.println("You can't go back.");
    }
    
    /**
     * Method called when the user wants to go to a different room.
     * @param arg0 Direction to go.
     * @param arg1 Boolean True if backtracking. If backtracking, method
     * 						doesn't log the movement in m_previousDirections.
     */
    private void goRoom(String arg0, boolean arg1)
    {
        //Get the Direction enum. If null, then it was invalid.
        Direction dir = Direction.getDirection(arg0);
        if(dir == null)
        {
            logger.error("goRoom(String) Invalid Direction");
            return;
        }
        
        //Check that the desired exit exists. If so, move the player and increment the turn counter.
        //If not, state that there is no exit.
        Room curRoom = m_labyrinth.getRoom(m_player.getLocation());
        if(curRoom.hasExit(dir))
        {
        	if(arg1==false)
        		m_previousDirections.add(dir);
        	m_player.setLocation(Direction.getLocation(dir, m_player.getLocation()));
            logger.println("You go " + dir.toString() + ".");
            describeRoom();
            m_turnCounter++;
        }else
            logger.println("There is no exit that way.");
    }
    
    /**
     * Method called for when the player gives up.
     * Print surrender text and set game end flag.
     */
    private void surrender()
    {
        logger.println("After " + m_turnCounter + " turns, you decided to give up.");
        logger.println("You were never going to find your way out, so why waste your " +
                        "time? Perhaps it is better to just wither away in the Labyrinth " +
                        "than to fruitlessly try to find your way out.");
        checkMap();
        m_gameEnd=true;
    }
    
    /**
     * Describes the room the player is currently in.
     */
    private void describeRoom()
    {
        logger.println(m_labyrinth.getRoom(m_player.getLocation()).getDescription() + "\n");
    }
    
    /**
     * Lists the exits for the room the player is currently in.
     */
    private void describeExits()
    {
        logger.println(m_labyrinth.getRoom(m_player.getLocation()).exitString()+"\n");
    }
    
    /**
     * 'Feels' the direction for the exit.
     */
    private void feelDirection()
    {
        double distance = Labyrinth.getRelativeDistance(m_player.getLocation(), m_destination);
        Direction direction = Direction.getRelativeDirection(m_player.getLocation(), m_destination);
        String strengthOfPull;
        if(distance <= 0.15)
        {
            strengthOfPull = "You feel a gut-wrenching pull to the ";
        }else if(distance <= 0.30)
        {
            strengthOfPull = "You feel strongly pulled to the ";
        }else if(distance <= 0.50)
        {
            strengthOfPull = "You feel a moderate pull to the ";
        }else if(distance <= 0.80)
        {
            strengthOfPull = "You feel a faint tugging to the ";
        }else
        {
            strengthOfPull = "You feel a nearly non-extistant tugging to the ";
        }
        logger.println(strengthOfPull + direction + ".\n");
    }
    
    /**
     * Lists all the commands the user can use.
     */
    private void help()
    {
        logger.println("");
        logger.println("go <direction>\t- Go in the specified direction. "
        				+ "Valid directions are North, South, East, West");
        logger.println("go back\t\t- Go to the previous room you were in if there is one.");
        logger.println("get item <item>\t- Picks up the specified item in the room.");
        logger.println("look\t\t- Get a description of the room.");
        logger.println("look exits\t- Look for the exits.");
        logger.println("check map\t- Check your map. The map is updated when you enter new rooms.");
        logger.println("check stats\t- Check your current physical condition.");
        logger.println("check inventory\t- Check your the items in your inventory.");
        logger.println("consume <item>\t- Consume the specified item.");
        logger.println("surrender\t- Give up.");
        logger.println("help\t\t- Print all valid commands.");
        logger.println("");
    }
}
