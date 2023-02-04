package theHunter.objects;

import luna2d.Game;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.lights.GlowLight;
import luna2d.maps.WorldPosition;
import luna2d.renderables.Sprite;
import theHunter.HunterWorldStruct;
import theHunter.ObjectTypes;
import theHunter.TheHunter;

public class Fire extends Sprite
{
	private final int FIRE_LIGHT_DISTANCE = 550;
	
	private GlowLight light;
	private boolean placing;
	
	public Fire(Scene inScene, boolean placingFire)
	{
		super(inScene, "Fire", 0, 0, 1, TheHunter.ENVIRONMENT_DRAW_LAYER, 16, 5, 100);
		this.enableCulling = false;
		this.setFixedScreenPosition(true);
		this.placing = placingFire;
	}
	
	public void placeOnGround(int x, int y)
	{
		this.placing = false;
		this.setFixedScreenPosition(false);
		this.enableCulling = true;
		this.updateWorldPosition(x, y);
		
		Vector2 gPos = Maths.convertToGrid(this.getWorldX(), this.getWorldY(), TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
		WorldPosition pWP = Maths.convertToWorldPosition(gPos, Game.CAMERA_SCALE, TheHunter.ROWS, TheHunter.COLUMNS);

		this.updateWorldPosition(pWP);
		
		if (this.getScene().getWorldData() != null)
		{
			HunterWorldStruct world = (HunterWorldStruct)this.getScene().getWorldData();
			world.addObjectToWorld(ObjectTypes.Fire, pWP);
		}
		
		light = new GlowLight(this.getScene(), 0, 0, FIRE_LIGHT_DISTANCE, this.getWorldPosition());
		light.visible = true;
	}
	
	@Override
	public void updateWorldPosition(WorldPosition wp)
	{
		super.updateWorldPosition(wp);
		
		if (this.light != null)
		{
			this.light.updateWorldPosition(wp, this.getWorldX(), this.getWorldY());
		}
	}
	
	public void placeOnGround()
	{
		Vector2 gPos = Maths.convertToGrid(Game.mouseWorldX, Game.mouseWorldY, TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
		
		int x = gPos.x * 16;
		int y = gPos.y * 16;
		
		this.placeOnGround(x, y);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if (this.light != null && this.light.visible)
		{			
			light.updateWorldPosition(this.getWorldX() + this.getWidth()/2, this.getWorldY() + this.getHeight()/2 + 4);
		}
		
		if (this.placing)
		{
			this.updateScreenPosition(Game.mouseX, Game.mouseY);
		}
	}
	
}
