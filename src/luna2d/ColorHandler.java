package luna2d;

import java.awt.Color;
import java.util.HashMap;

public class ColorHandler 
{
	private static HashMap<String, Color> colors = new HashMap<String, Color>();
	
	public static void addColor(String name, Color c)
	{
		colors.put(name, c);
	}
	
	public static Color getColor(String name)
	{
		return colors.get(name);
	}
	
}
