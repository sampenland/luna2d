package luna2d;

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
	
}
