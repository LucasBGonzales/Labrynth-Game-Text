package items;

/**
 * @author Lucas Gonzales
 * @version 29.11.2017
 * • Created class
 * • Added Constructor to set all the values for this consumable.
 * • Instantiated getDescription.
 * 
 * @version 10.12.2017
 * • Added getEffectDescription override.
 */

public class RedApple extends Consumable{

	public RedApple()
	{
		this.setDrinkValue(3.5f);
		this.setFoodValue(17.5f);
		this.setHealValue(1.3f);
		this.setName("Red Apple");
		registerItem();
	}
	
	@Override
	public String getDescription() {
		String describe;
		describe = "It is a juicy " + getName() + ". Looks pretty clean.";
		return describe;
	}

	@Override
	String getEffectDescription() {
		String describe;
		describe = "Some hunger is satiated. The juiciness of the apple was nice.";
		return describe;
	}

}
