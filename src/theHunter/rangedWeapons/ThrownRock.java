package theHunter.rangedWeapons;

import luna2d.Scene;
import luna2d.Vector2f;
import theHunter.HunterWorldStruct;
import theHunter.ObjectTypes;
import theHunter.TheHunter;
import theHunter.objects.Rock;

public class ThrownRock extends RangedWeapon
{
	private static final int FORCE = 12;
	public ThrownRock(Scene inScene, int x, int y, int scale, Vector2f velocity, float friction, int depth) 
	{
		super(inScene, "Rock", x, y, scale, depth, velocity.x * FORCE, velocity.y * FORCE, friction);
	}
	
	private void stop()
	{
		Rock rock = new Rock(this.getScene(), this.getWorldX(), this.getWorldY(), 1, TheHunter.ENVIRONMENT_DRAW_LAYER);
		this.getScene().addSprite(rock);
		HunterWorldStruct world = (HunterWorldStruct)this.getScene().getWorldData();
		world.addObjectToWorld(ObjectTypes.Rock, rock.getWorldPosition());
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
