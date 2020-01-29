package entities;

import utility.ValueBar;

/**
 * ThirstBar is a ValueBar that tracks stages of thirst and provides a description for the feeling
 * of thirst.
 * @author Lucas Gonzales
 * @version 28.11.2017
 * • Created the class
 * • [Done] Added describeValue
 * • [Done] Added getThirstStage
 */

public class ThirstBar extends ValueBar{

	public ThirstBar(float arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets stage of hunger
	 * @return
	 */
	@Override
	public int getStage()
	{
		int stage;
		if(value() > 90.0f) // x > 90%
			stage = 6;
		else if(value() > 70.0f) // 90 > x > 70%
			stage = 5;
		else if(value() > 50.0f) // 70% >= x > 50%
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
		
		if(stage == 6) // x > 90%
			describe = "You are well hydrated.";
		else if(stage == 5) // 90 > x > 70%
			describe = "You feel a bit thirsty, but not too bad.";
		else if(stage == 4) // 70% > x > 50%
			describe = "You're getting pretty thirsty.";
		else if(stage == 3) // 50% => x > 30%
			describe = "Your head hurts, your mouth is dry and your lips are getting cracked... You need to drink.";
		else if(stage == 2) // 30% => x > 10%
			describe = "Is the room spinning? Or is that just you? Your head hurts really bad. Need a drink...";
		else if(stage == 1) // 10% => x > 0%
			describe = "Your head is throbbing painfully. You feel weak and can swear the room is spinning.";
		else // 0%
			describe = "You are actively dying of dehydration.";
			
		return describe;
	}

}
