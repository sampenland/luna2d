package theHunter.objects;

import luna2d.Game;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.lights.GlowLight;
import luna2d.renderables.Sprite;
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
		
		// TODO: Fix this for no bug when placing fire (also do to fence and growing bush)
		//this.updateWorldPosition(Maths.convertToWorldPosition(x, y, TheHunter.CELL_SIZE * Game.CAMERA_SCALE, 1, TheHunter.ROWS, TheHunter.COLUMNS));
		
		light = new GlowLight(this.getScene(), 0, 0, FIRE_LIGHT_DISTANCE);
		light.visible = true;
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
