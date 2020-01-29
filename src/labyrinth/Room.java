package labyrinth;

import java.util.ArrayList;

import items.Inventory;

/**
 * This is the Room object which contains all description data for each room in the labyrinth.
 * @author Lucas Gonzales
 * @version 23.11.2017
 * @version 26.11.2017
 * • Added addDescription to account for new method of generating a description.
 * 		• This renders hasDescriptor and addDescriptor obsolete to the game.
 * @version 4.12.2017
 * • Added m_isKnown field
 * • Added isKnown(boolean) and isknown(void) to for m_isKnown field modifier and accessor.
 */

public class Room {
	private ArrayList<String> m_description;
	private ArrayList<Direction> m_exits;
	private Inventory m_items;
	private boolean m_isKnown;
	
	public Room()
	{
		//Starts all closed.
		m_exits = new ArrayList<Direction>();
		m_description = new ArrayList<String>();
		m_items = new Inventory();
	}
	
	public void isKnown(boolean arg0)
	{ m_isKnown = arg0;}
	
	public boolean isKnown()
	{ return m_isKnown;}
	
	public void addExit(Direction arg0)
	{
		//Cheap way to prevent duplicates
		removeExit(arg0);
		m_exits.add(arg0);
	}
	
	public boolean hasExit(Direction arg0)
	{ return m_exits.contains(arg0); }
	
	public Inventory items()
	{ return m_items; }
	
	public ArrayList<Direction> getOpenExits()
	{ return m_exits; }

	public ArrayList<Direction> getSealedExits()
	{
		ArrayList<Direction> exits = getOpenExits();
		ArrayList<Direction> sealedExits = new ArrayList<Direction>();
		for(int i=0; i < Direction.values().length; i++)
		{
			if(exits.contains(Direction.values()[i]) == false)
				sealedExits.add(Direction.values()[i]);
		}
		return sealedExits;
	}
	
	public void removeExit(Direction arg)
	{
		for(int i=0; i < m_exits.size(); i++)
		{
			if(m_exits.get(i).equals(arg))
				m_exits.remove(i);
		}
	}
	
	public void addDescriptor(String arg0)
	{ m_description.add(arg0); }
	
	public void addDescription(ArrayList<String> arg0)
	{ m_description.addAll(arg0); }
	
	public boolean hasDescriptor(String arg0)
	{ return m_description.contains(arg0); }
	
	public boolean isClosedOff()
	{ return m_exits.size() == 0; }
	
	public String getDescription()
	{
		String description="";
		//Describe the room
		for(int i=0; i < m_description.size(); i++)
			description += m_description.get(i) + "\n";
		//Describe the items in the room.
		if(items().getList().size() > 0)
			description+="You see ";
		for(int i=0; i < items().getList().size(); i++)
		{
			description+="a " + items().getList().get(i).getName();
			if(i == items().getList().size()-1)
				description+=".\n";
			else
				description+=", ";
		}
		//State the Exits.
		if(isClosedOff())
			description+="You see no exits.";
		else
		{
			description+="You can see exits to the ";
			boolean doComma=false; 
			for(int i=0; i<m_exits.size(); i++)
			{
				if(doComma)
					description+=", ";
				description+=m_exits.get(i).toString();
				doComma=true;
				if(i == m_exits.size()-1)
					doComma=false;
			}
			description+=".";
		}
		return description;
	}
	
	public String exitString()
	{
		String str = "Exits: ";
		if(m_exits.size()==0)
			str += "None";
		for(int i=0; i < m_exits.size(); i++)
		{
			str += m_exits.get(i).toString() + " ";
		}
		return str;
	}
}
