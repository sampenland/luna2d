package theHunter.objects;

import luna2d.Scene;
import luna2d.lights.GlowLight;
import luna2d.renderables.Sprite;

public class Fire extends Sprite
{
	GlowLight light;
	public Fire(Scene inScene, int x, int y, int scale, int depth)
	{
		super(inScene, "Fire", x, y, scale, depth, 16, 5, 100);
		light = new GlowLight(inScene, 0, 0, 500);
	}
	
	@Override
	public void update()
	{
		super.update();
		light.updateWorldPosition(this.getWorldX() + this.getWidth()/2, this.getWorldY() + this.getHeight()/2 + 4);
	}
	
}
