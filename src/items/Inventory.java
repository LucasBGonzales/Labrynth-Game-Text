package items;

import java.util.ArrayList;

/**
 * Inventory holds a collection of Items and provides access to the items.
 * @author Lucas Gonzales
 * 
 * @version 28.11.2017
 * • Created this class
 * • [Done] Added getList
 * 
 * @version 29.11.2017
 * • Added retrieveItem
 * • Added addItem
 * • Added addItems
 */

public class Inventory {
	ArrayList<Item> m_items;
	
	public Inventory()
	{
		m_items = new ArrayList<Item>();
	}
	
	/**
	 * If an item exists, this method finds it, removes it from the
	 * inventory, then returns the item removed.
	 * @param arg0 Name of item to retrieve.
	 * @return Item found, or null if no item is found.
	 */
	public Item retrieveItem(String arg0)
	{
		String itemToFind = arg0.toLowerCase();
		Item retrievedItem = null;
		for(int i=0; i < m_items.size(); i++)
		{
			String thisItem = m_items.get(i).getName().toLowerCase();
			if(thisItem.equals(itemToFind))
			{
				retrievedItem =  m_items.get(i);
				m_items.remove(i);
				break;
			}
		}
		return retrievedItem;
	}
	
	public void addItems(ArrayList<Item> arg0)
	{ m_items.addAll(arg0); }
	
	public void addItem(Item arg0)
	{
		if(arg0 != null)
			m_items.add(arg0);
	}
	
	public String listItems()
	{
		String list="";
		
		for(int i=0; i < m_items.size(); i++)
		{
			list += m_items.get(i).getName();
			if(i < m_items.size()-1)
				list+=", ";
		}
		
		return list;
	}
	
	/**
	 * @return The ArrayList of Items in this inventory.
	 */
	public ArrayList<Item> getList()
	{ return m_items; }
}
