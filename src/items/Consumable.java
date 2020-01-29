package items;

import entities.Entity;

/**
 * @author Lucas Gonzales
 * @version 28.11.2017
 * • Added fields m_foodValue, m_drinkValue, m_healValue
 * • [Done] Added getFoodValue
 * • [Done] Added setFoodValue
 * • [Done] Added getDrinkValue
 * • [Done] Added setDrinkValue
 * • [Done] Added getHealValue
 * • [Done] Added setHealValue
 * 
 * @version 10.12.2017
 * • Added getEffectDescription abstract method.
 */
public abstract class Consumable extends Item{
	protected float m_foodValue;
	protected float m_drinkValue;
	protected float m_healValue;
	
	/**
	 * @param arg0 Entity to consume this item.
	 */
	public String consume(Entity arg0)
	{
		arg0.getHealth().modValue(m_drinkValue);
		arg0.getHunger().modValue(m_drinkValue);
		arg0.getThirst().modValue(m_drinkValue);
		
		return getEffectDescription();
	}
	
	/**
	 * Describes the feeling of consuming this consumable.
	 * Should be instantiated by any extending class.
	 * @return Feeling of consuming this consumable.
	 */
	abstract String getEffectDescription();
	
	public float getFoodValue()
	{ return m_foodValue; }
	
	public float getDrinkValue()
	{ return m_drinkValue; }
	
	public float getHealValue()
	{ return m_healValue; }

	protected void setFoodValue(float arg0)
	{ m_foodValue = arg0; }
	
	protected void setDrinkValue(float arg0)
	{ m_drinkValue = arg0; }
	
	protected void setHealValue(float arg0)
	{ m_healValue = arg0; }
	
}
