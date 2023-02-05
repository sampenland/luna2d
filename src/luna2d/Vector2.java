package luna2d;

public class Vector2 
{
	public int x, y;
	
	public Vector2(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public static int compareXYdistanceReturnGreatest(Vector2 one, Vector2 two)
	{
		boolean x = Math.abs(one.x - two.x) > Math.abs(one.y - two.y);
		
		if (x)
		{
			return one.x + two.x;
		}
		else
		{
			return one.y + two.y;
		}
	}
	
	public static Vector2 add(Vector2 one, Vector2 two)
	{
		return new Vector2(one.x + two.x, one.y + two.y); 
	}
}
