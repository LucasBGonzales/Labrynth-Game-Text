package utility;

/**
 * ValueBar holds a value and contains public methods to make the modification
 * and access of the value easy. It contains a bounds to easily limit the value.
 * @author Lucas Gonzales
 * @version 28.11.2017
 * • Created this class
 * • Added fields m_value, m_maxValue, m_minValue
 * • Added constants DEFAULT_MAX_VALUE, DEFAULT_MIN_VALUE
 * • [Done] Added checkBounds
 * • [Done] Added modValue
 * • [Done] Added setValue
 * • [Done] Added value
 */

public class ValueBar {
	protected static final float DEFAULT_MAX_VALUE = 100.0f;
	protected static final float DEFAULT_MIN_VALUE = 0.0f;
	//As a percentage.
	private float m_value;
	//Greatest value m_value can be
	private float m_maxValue;
	//Lowest value m_value can be
	private float m_minValue;
	
	/**
	 * Constructor for ValueBar.
	 * @param arg0 The initial value of this ValueBar
	 */
	public ValueBar(float arg0)
	{ this(arg0, DEFAULT_MAX_VALUE, DEFAULT_MIN_VALUE); }
	
	/**
	 * Constructor for ValueBar.
	 * @param arg0 The initial value of this ValueBar
	 * @param arg1 Greatest value m_value can be.
	 * @param arg2 Lowest value m_value can be.
	 */
	public ValueBar(float arg0, float arg1, float arg2)
	{
		m_value = arg0;
		m_maxValue = arg1;
		m_minValue = arg2;
		checkBounds();
	}
	
	private void checkBounds()
	{
		if(m_value > m_maxValue)
			m_value = m_maxValue;
		else if(m_value < m_minValue)
			m_value = m_minValue;
	}
	
	/**
	 * This method should be overridden by extending classes
	 * to properly describe the values.
	 * @return
	 */
	public String describeValue()
	{ return "Value is " + value(); }
	
	/**
	 * Gets stage of the ValueBar
	 * By Default: 
	 * Stage 5 = value > 80%
	 * Stage 4 = 80 >= value > 60%
	 * Stage 3 = 60% => value > 40%
	 * Stage 2 = 40% => value > 20%
	 * Stage 1 = 20% => value > 0%
	 * Stage 0 = value == 0
	 * @return
	 */
	public int getStage()
	{
		int stage;
		
		if(value() > 80.0f) // x > 80%
			stage = 5;
		else if(value() > 60.0f) // 80 >= x > 60%
			stage = 4;
		else if(value() > 40.0f) // 60% => x > 40%
			stage = 3;
		else if(value() > 20.0f) // 40% => x > 20%
			stage = 2;
		else if(value() > 0.0f) // 20% => x > 0%
			stage = 1;
		else // 0%
			stage = 0;
		return stage;
	}
	
	public void modValue(float arg0)
	{
		m_value += arg0;
		checkBounds();
	}
	
	public void setValue(float arg0)
	{
		m_value = arg0;
		checkBounds();
	}
	
	/**
	 * Accessor method for the value of this bar.
	 * @return float m_value
	 */
	public float value()
	{ return m_value; }
}
