package items;

/**
 * 
 * @author Lucas
 * @version 10.12.2017
 * • Created class. Added getDescription and getEffectDescription.
 */

public class HealthPotion extends Consumable{

	public HealthPotion()
	{
		this.setDrinkValue(0.5f);
		this.setFoodValue(0f);
		this.setHealValue(46f);
		this.setName("Health Potion");
		registerItem();
	}
	
	@Override
	public String getDescription() {
		String describe;
		describe = "It is a Health Potion. This should make you feel a little better.";
		return describe;
	}

	@Override
	String getEffectDescription() {
		String describe;
		describe = "You feel moderately better. Cuts and bruises heal over.";
		return describe;
	}

}
