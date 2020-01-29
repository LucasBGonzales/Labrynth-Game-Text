package entities;

import labyrinth.Location;

/**
 * Player class holds all the data about the player.
 * This includes inventory, health, hungry, thirst.
 * @author Lucas Gonzales
 * @version 28.11.2017
 * • Created this class
 * • Added fields m_inventory, m_hunger, m_thirst, m_health
 * • [Done] Added getHealth
 * • [Done] Added getHunger
 * • [Done] Added getThirst
 * 
 * @version 29.11.2017
 * • Added getInventory
 * • Converted types of fields m_hunger, m_thirst, m_health to their respective bars
 *   from the superclass ValueBar
 *   
 * @version 30.11.2017
 * • Player now extends Entity, which handles things all Entities should have
 * such as health
 */

public class Player extends Entity{
	
	public Player(Location arg0)
	{
		m_hunger = new HungerBar(100f);
		m_thirst = new ThirstBar(100f);
		m_health = new HealthBar(100f);
		m_location = arg0;
	}
}
