package theHunter.objects;

import luna2d.Game;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.lights.GlowLight;
import luna2d.renderables.Sprite;
import theHunter.TheHunter;
import theHunter.WorldPosition;

public class Torch extends Sprite
{
	private final int TORCH_LIGHT_DISTANCE = 350;
	
	private GlowLight light;
	private boolean placing;
	
	public Torch(Scene inScene, boolean placingTorch)
	{
		super(inScene, "Torch", 0, 0, 1, TheHunter.ENVIRONMENT_DRAW_LAYER, 16, 5, 150);
		this.enableCulling = false;
		this.setFixedScreenPosition(true);
		this.placing = placingTorch;
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
		
		light = new GlowLight(this.getScene(), 0, 0, TORCH_LIGHT_DISTANCE, this.getWorldPosition());
		light.visible = true;
	}
	
	@Override
	public void updateWorldPosition(WorldPosition wp)
	{
		super.updateWorldPosition(wp);
		
		if (this.light != null)
		{
			this.light.updateWorldProsition(wp);
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
			light.updateWorldPosition(this.getWorldX() + this.getWidth()/2, this.getWorldY() + this.getHeight()/2 - 16);
		}
		
		if (this.placing)
		{
			this.updateScreenPosition(Game.mouseX, Game.mouseY);
		}
	}
	
}
