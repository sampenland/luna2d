package luna2d;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

public class Maths 
{

	public static int clamp(int val, int max, int min)
	{
		if (val >= max) return val = max;
		if (val <= min) return val = min;
		return val;
	}
	
	public static float clamp(float val, float max, float min)
	{
		if (val >= max) return val = max;
		if (val <= min) return val = min;
		return val;
	}
	
	public static int random(int min, int max)
	{
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static Point convertToGrid(int x, int y, int cellSize, int startX, int startY)
	{
		Point p = new Point(-1, -1);
		
		if (startX < 0) startX = 0;
		if (startY < 0) startY = 0;
		
		// x - 8 and y - 32 fixes the window padding issue
		p.x = Math.round(((x - 8) - startX) / cellSize);
		p.y = Math.round(((y - 32) - startY) / cellSize);
		
		return p;
	}
}
