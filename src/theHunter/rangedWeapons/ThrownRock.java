package theHunter.rangedWeapons;

import luna2d.Log;
import luna2d.Scene;
import theHunter.objects.Rock;

public class ThrownRock extends RangedWeapon
{

	public ThrownRock(Scene inScene, int x, int y, int scale, float velX, float velY, float friction) 
	{
		super(inScene, "Rock", x, y, scale, velX, velY, friction);
	}
	
	private void stop()
	{
		this.getScene().addSprite(new Rock(this.getScene(), this.worldX, this.worldY, 1));
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
