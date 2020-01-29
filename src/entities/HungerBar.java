package entities;

import utility.ValueBar;

/**
 * HungerBar is a ValueBar that tracks stages of hunger and provides a description for the feeling
 * of hunger.
 * @author Lucas Gonzales
 * @version 28.11.2017
 * • Created the class
 * • [Done] Added describeValue
 * • [Done] Added getHungerStage
 */

public class HungerBar extends ValueBar{

	/**
	 * Constructor. Allows hunger to go to 110% as overeating.
	 * @param arg0 Beginning value.
	 */
	public HungerBar(float arg0) {
		super(arg0, 110.0f, DEFAULT_MIN_VALUE);
	}
	
	/**
	 * Gets stage of hunger
	 * @return
	 */
	@Override
	public int getStage()
	{
		int stage;
		if(value() > 100.0f) // > 100%
			stage = 6;
		else if(value() == 100.0f) // 100%
			stage = 5;
		else if(value() > 50.0f) // 100% > x > 50%
			stage = 4;
		else if(value() > 30.0f) // 50% => x > 30%
			stage = 3;
		else if(value() > 10.0f) // 30% => x > 10%
			stage = 2;
		else if(value() > 0.0f) // 10% => x > 0%
			stage = 1;
		else // 0%
			stage = 0;
		return stage;
	}

	@Override
	public String describeValue() {
		String describe;
		int stage = getStage();
		
		if(stage == 6) // > 100%
			describe = "You feel like you have eaten too much.";
		else if(stage == 5) // 100%
			describe = "You feel perfectly well-fed.";
		else if(stage == 4) // 100% > x > 50%
			describe = "You feel well-fed.";
		else if(stage == 3) // 50% => x > 30%
			describe = "You feel a bit hungry.";
		else if(stage == 2) // 30% => x > 10%
			describe = "Your stomach is growling.";
		else if(stage == 1) // 10% => x > 0%
			describe = "You are painfully hungry. You should eat something soon.";
		else // 0%
			describe = "You are actively starving.";
			
		return describe;
	}

}
