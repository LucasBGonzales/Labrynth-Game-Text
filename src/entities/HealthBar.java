package entities;

import utility.ValueBar;

/**
 * HealthBar is a ValueBar that tracks an entity's health and provides a description for how hurt the
 * entity is.
 * @author Lucas Gonzales
 * @version 28.11.2017
 * • Created the class
 * • [Done] Added describeValue
 * • [Done] Added getHealthStage
 */

public class HealthBar extends ValueBar{

	public HealthBar(float arg0) {
		super(arg0);
	}
	
	/**
	 * Gets stage of Health
	 * @return
	 */
	@Override
	public int getStage()
	{
		int stage;
		
		if(value() > 90.0f) // x > 90%
			stage = 5;
		else if(value() > 70.0f) // 90 >= x > 70%
			stage = 4;
		else if(value() > 30.0f) // 70% => x > 30%
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
		
		if(stage == 5) // 100%
			describe = "You are healthy.";
		else if(stage == 4) // 90% > x > 70%
			describe = "You feel okay. You could certainly be better, but you'll live.";
		else if(stage == 3) // 70% => x > 30%
			describe = "In the moments between the dull throbs of your heartbeat, the pain abates. Your not sure "
					+ "if you are grateful for those brief reprieves or annoyed by the inconsistency of it all.";
		else if(stage == 2) // 30% => x > 10%
			describe = "Your body aches terribly. You desperately want something to ease the pain.";
		else if(stage == 1) // 10% => x > 0%
			describe = "The pain doesn't hurt so much anymore, like you've gone numb. Your vision "
					+ "is growing faint. All you want to do is sleep...";
		else // 0%
			describe = "You are dead.";
			
		return describe;
	}

}
