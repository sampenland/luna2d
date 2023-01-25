package theHunter.objects;

import luna2d.Scene;
import luna2d.renderables.Sprite;

public class WaterSource extends Sprite
{
	public WaterSource(Scene inScene, int x, int y, int scale, int depth)
	{
		super(inScene, "Water", x, y, scale, depth);
	}
	
	@Override
	public void update()
	{
		super.update();		
	}
	
}
