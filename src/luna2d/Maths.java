package luna2d;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import luna2d.playerControllers.SimplePlayer;

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
	
	public static Point directionBetweenTwoPoints(Point start, Point end)
	{
		return new Point((end.x - start.x), (end.y - start.y));
	}
	
	public static int random(int min, int max)
	{
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static int distanceBetweenTwoPoints(Point p1, Point p2)
	{
		return (int) Point.distance(p1.x, p1.y, p2.x, p2.y);
	}
	
	public static int distanceBetweenPlayerAndPoint(Object playerObj, Point p)
	{
		if (playerObj instanceof SimplePlayer)
		{
			SimplePlayer player = (SimplePlayer)playerObj;
			int d = (int)Point.distance(player.worldX, player.worldY, p.x, p.y);
			return d;
		}
		
		return 0;
	}
	
	public static Point convertToGrid(int x, int y, int cellSize, int startX, int startY)
	{
		Point p = new Point(-1, -1);
		
		if (startX < 0) startX = 0;
		if (startY < 0) startY = 0;
		
		// x - 8 and y - 32 fixes the window padding issue
		p.x = Math.round((x - startX) / cellSize) * Game.CAMERA_SCALE;
		p.y = Math.round((y - startY) / cellSize) * Game.CAMERA_SCALE;
		
		return p;
	}
	
	public static boolean characterIsAlphaNumeric(int character, boolean allowBackspace)
	{
		if (allowBackspace && character == KeyEvent.VK_BACK_SPACE) return true;
		return ((character >= (int)'0' & character <= (int)'9') || (character >= (int)'a' && character <= (int)'z') || (character >= (int)'A' && character <= (int)'Z'));
	}
}
