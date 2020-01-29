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

public class RottenApple extends Consumable{

	public RottenApple()
	{
		this.setDrinkValue(1.0f);
		this.setFoodValue(3.5f);
		this.setHealValue(-3.7f);
		this.setName("Rotten Apple");
		registerItem();
	}
	
	public String getDescription() {
		String describe;
		describe = "It is a + " + getName() + ". Are those maggots in there?";
		return describe;
	}

	@Override
	String getEffectDescription() {
		String describe;
		describe = "You feel a little sick after eating the Rotten Apple, "
				+ "but it satisfied some of your hunger.";
		return describe;
	}
}
