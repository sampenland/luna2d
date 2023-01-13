package theHunter;

import java.awt.Color;

import luna2d.Scene;
import luna2d.renderables.Rect;

public class Ground extends Rect
{

	public Ground(Scene inScene, int x, int y, ObjectTypes gndType) 
	{
		super(inScene, x, y, 16, 16, true, TheHunter.GrassColor);	
	}
	
	public void updateGroundType(ObjectTypes t)
	{
		Color c = TheHunter.GrassColor;
		
		switch(t)
		{
		case Bush:
			break;
		case Empty:
			break;
		case GndDirt:
			c = TheHunter.DirtColor;
			break;
		case GndGrass:
			c = TheHunter.GrassColor;
			break;
		case GndRock:
			break;
		case GndWater:
			c = TheHunter.WaterColor;
			break;
		case Player:
			break;
		case Tree:
			break;
		case Water:
			break;
		case Wolf:
			break;
		default:
			break;
		
		}
		
		this.updateColor(c);
	}

}
