package theHunter.objects;

import luna2d.Scene;
import luna2d.renderables.Sprite;

public class Wolf extends Sprite
{
	public Wolf(Scene inScene, int x, int y, int scale, int depth)
	{
		super(inScene, "Wolf", x, y, scale, depth);
	}
	
	@Override
	public void update()
	{
		super.update();		
	}
	
}
