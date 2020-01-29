package entities;

import items.Inventory;
import labyrinth.Location;

/**
 * 
 * @author Lucas
 * 
 * @version 5.12.2017
 * • Added m_location.
 * 
 * @version 10.12.2017
 * • Added set methods for hunger, health, thirst.
 */

public abstract class Entity {
	protected HealthBar m_health;
	protected HungerBar m_hunger;
	protected ThirstBar m_thirst;
	protected Inventory m_inventory;
	protected Location m_location;
	
	public Entity()
	{
		m_inventory = new Inventory();
	}
	
	public Inventory items()
	{ return m_inventory; }
	
	public HealthBar getHealth()
	{ return m_health; }
	
	public HungerBar getHunger()
	{ return m_hunger; }
	
	public ThirstBar getThirst()
	{ return m_thirst; }
	
	public void setHealth(float arg0)
	{ getHealth().setValue(arg0); }
	
	public void setHunger(float arg0)
	{ getHunger().setValue(arg0); }
	
	public void setThirst(float arg0)
	{ getThirst().setValue(arg0); }
	
	public void modHealth(float arg0)
	{ getHealth().modValue(arg0); }
	
	public void modHunger(float arg0)
	{ getHunger().modValue(arg0); }
	
	public void modThirst(float arg0)
	{ getThirst().modValue(arg0); }
	
	public String describeStats()
	{
		String describe="";
		describe+=m_hunger.describeValue();
		describe+=m_thirst.describeValue();
		describe+=m_health.describeValue();
		return describe;
	}
	
	public Location getLocation()
	{ return m_location; }
	
	public void setLocation(Location arg0)
	{ m_location = arg0; }
}
