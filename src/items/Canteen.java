package items;

import entities.Entity;
import utility.ValueBar;

/**
 * 
 * @author Lucas Gonzales
 * @version 10.12.2017
 * • Added overridden consume method.
 */

public class Canteen extends Consumable {
	public static final boolean CLEAN = true;
	public static final boolean DIRTY = false;
	private static final float DRINK_AMOUNT = 40f;
	private ValueBar m_valueBar;
	private boolean m_waterQuality;
	
	/**
	 * Constructor for Canteen.
	 */
	public Canteen() {
		this(0);
	}
	
	/**
	 * Constructor for Canteen.
	 * @param arg0 How much water is in the Canteen to begin with.
	 */
	public Canteen(float arg0) {
		this(arg0,CLEAN);
	}
	
	/**
	 * Constructor for Canteen.
	 * @param arg0 How much water is in the Canteen to begin with.
	 * @param arg1 Whether water is clean or dirty.
	 */
	public Canteen(float arg0, boolean arg1) {
		setName("Canteen");
		m_valueBar = new ValueBar(arg0);
		m_waterQuality=arg1;
		registerItem();
	}
	
	public float value()
	{ return m_valueBar.value(); }

	public String getDescription() {
		String describe;
		
		if(m_waterQuality)
			describe = "It is a Canteen of clean water. ";
		else
			describe = "It is a Canteen of dirty water. ";
		
		int stage = m_valueBar.getStage();
		if(stage == 0)
			describe += "It is depressingly empty.";
		else if(stage == 1)
			describe += "There is maybe a mouthful left.";
		else if(stage == 2)
			describe += "It is running low.";
		else if(stage == 3)
			describe += "It is about half full.";
		else if(stage == 4)
			describe += "It is mostly full.";
		else //stage == 5
			describe += "It is full.";
		
		return describe;
	}

	@Override
	String getEffectDescription() {
		String describe="";
		if(m_waterQuality == CLEAN)
			describe = "You took a refreshing sip of clean water.";
		else if(m_waterQuality == DIRTY)
			describe = "You took a sip of dirty water. You feel as though if it were clean "
					+ "the effect would have been more satisfying";
		return describe;
	}
	
	/**
	 * Drink from the canteen.
	 * @param arg0 The amount to drink
	 * @return The amount that was drunk. If the amount of water in 
	 * 			the canteen was less than arg0, then this will be
	 * 			how much water was available.
	 */
	@Override
	public String consume(Entity arg0)
	{
		//Store current value
		float drinkValue = m_valueBar.value();
		
		//If there is no water, say so and return.
		if(drinkValue == 0f)
			return "There is no water left.";
		
		//Mod the value.
		m_valueBar.modValue(-DRINK_AMOUNT);
		
		//Check if the amount to drink is less than DRINK_AMOUNT.
		//If yes, drink that much. If no, drink DRINK_AMOUNT
		if(drinkValue > DRINK_AMOUNT)
			drinkValue = DRINK_AMOUNT;
		
		//Check if the water is dirty. If so, only satisfy a fraction of thirst.
		if(m_waterQuality == DIRTY)
			drinkValue *= 0.7f;
		
		//drink and describe effect.
		arg0.modThirst(drinkValue);
		return getEffectDescription();
	}
	
	/**
	 * Fill the Canteen with water, dirty or clean.
	 * Dirty water, even a little, will contaminate the rest.
	 * @param arg0 Amount of water
	 * @param arg1 True is clean water, False is dirty water.
	 */
	public void fill(float arg0, boolean arg1)
	{
		m_valueBar.modValue(arg0);
		if(arg1 == DIRTY)
			m_waterQuality = DIRTY;
	}
}

