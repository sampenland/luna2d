package luna2d;

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
}
