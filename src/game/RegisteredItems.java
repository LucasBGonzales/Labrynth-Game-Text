package game;

import java.util.ArrayList;
import items.Consumable;

public abstract class RegisteredItems {
	private static ArrayList<Object> m_classes = new ArrayList<Object>();
	
	public static void registerItem(Object arg0)
	{
		if(m_classes.contains(arg0))
			m_classes.add(arg0);
	}
	
	public static Object getItem()
	{
		int num = (int) (Math.random() * m_classes.size());
		return m_classes.get(num);
	}
	
	public static Consumable getConsumable()
	{
		ArrayList<Consumable> consumables = new ArrayList<Consumable>();
		m_classes.stream().forEach(c -> 
		{
			if(c instanceof Consumable)
				consumables.add((Consumable)c);
		}
		);
		int num = (int) (Math.random() * consumables.size());
		return consumables.get(num);
	}
	
	public static String getClassesString()
	{
		String strReturn = "";
		for(Object s : m_classes.toArray())
			strReturn += s.toString();
		/*for(int i=0; i < m_classes.size(); i++)
			strReturn+=m_classes.get(i).toString();*/
		return strReturn;
	}
}
