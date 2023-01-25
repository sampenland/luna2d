package theHunter.rangedWeapons;

import luna2d.Log;
import luna2d.Scene;
import luna2d.Vector2;
import theHunter.TheHunter;
import theHunter.objects.Rock;

public class ThrownRock extends RangedWeapon
{
	private static final int FORCE = 12;
	public ThrownRock(Scene inScene, int x, int y, int scale, Vector2 velocity, float friction, int depth) 
	{
		super(inScene, "Rock", x, y, scale, depth, velocity.x * FORCE, velocity.y * FORCE, friction);
	}
	
	private void stop()
	{
		this.getScene().addSprite(new Rock(this.getScene(), this.getWorldX(), this.getWorldY(), 1, TheHunter.ENVIRONMENT_DRAW_LAYER));
		this.destroy();
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if (this.velocityX < 1 && this.velocityX > -1 && this.velocityY < 1 && this.velocityY > -1)
		{
			this.stop();
		}
		
	}

}
