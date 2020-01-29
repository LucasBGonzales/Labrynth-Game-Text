package items;

import java.util.ArrayList;

import game.RegisteredItems;

/**
 * @author Lucas Gonzales
 * @version 28.11.2017
 * • Created this class
 * • Added field m_name
 * • [Done] Added setName
 * • [Done] Added getName
 * • [Done] Added getDescription
 */

public abstract class Item {
	protected String m_name;
	
	/**
	 * Sets the name of this item to arg0
	 * @param arg0 The new name of this item.
	 */
	protected void setName(String arg0)
	{ m_name = arg0; }
	
	/**
	 * @return The name of this item.
	 */
	public String getName()
	{ return m_name; }
	
	/**
	 * Register this item to be used in the game
	 */
	public void registerItem()
	{
		RegisteredItems.registerItem(this.getClass());
	}
	
	/**
	 * This method should be instantiated by all classes which extend
	 * Item to return an appropriate description of the item.
	 * @return
	 */
	abstract public String getDescription();
}
