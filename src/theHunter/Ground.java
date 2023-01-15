package theHunter;

import java.awt.Color;

import luna2d.ColorHandler;
import luna2d.Scene;
import luna2d.renderables.Rect;

public class Ground extends Rect
{

	private int objectType;
	
	public Ground(Scene inScene, int x, int y) 
	{
		super(inScene, x, y, 16, 16, true, ColorHandler.getColor("GrassGreen"));
		this.objectType = ObjectTypes.GndGrass.intValue;
	}
	
	public int getObjectType()
	{
		return this.objectType;
	}
	
	public void updateGroundType(ObjectTypes t)
	{
		this.objectType = t.intValue;
		
		Color c = ColorHandler.getColor("GrassGreen");
		
		switch(t)
		{
		case Bush:
			break;
		case Empty:
			break;
		case GndDirt:
			break;
		case GndGrass:
			c = ColorHandler.getColor("GrassGreen");
			break;
		case GndRock:
			break;
		case GndWater:
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
