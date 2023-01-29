package theHunter.objects;

import luna2d.Game;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.renderables.Sprite;
import theHunter.TheHunter;

public class Fence extends Sprite
{
	private boolean placing;
	private boolean isVert;
	
	public Fence(Scene inScene) 
	{
		super(inScene, "Fence", 0, 0, 1, TheHunter.ENVIRONMENT_DRAW_LAYER, 16, 6, 0);
		this.enableCulling = false;
		this.setFixedScreenPosition(true);
		this.placing = true;
		this.isVert = true;
		this.mouseEnabled = true;
	}
	
	public void placeOnGround()
	{
		Vector2 gPos = Maths.convertToGrid(Game.mouseWorldX, Game.mouseWorldY, TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
				
		this.placing = false;
		this.setFixedScreenPosition(false);
		this.updateWorldPosition(gPos.x * 16, gPos.y * 16);
	}
	
	public void pickup()
	{
		this.placing = true;
		this.setFixedScreenPosition(true);
	}
	
	public void rotate()
	{
		if (this.isVert)
		{
			this.setFrame(3, false);
		}
		else
		{
			this.setFrame(0, false);
		}
		
		this.isVert = !this.isVert;
	}
	
	private void checkClicks()
	{
		if (this.mouseClicked)
		{
			if (this.mouseClickEvent != null)
			{
				if (this.mouseClickEvent.getButton() == 3)
				{
					this.rotate();
				}
			}
		}
	}
	
	@Override
	public void update()
	{
		super.update();
		
		if (this.placing)
		{			
			this.updateScreenPosition(Game.mouseX, Game.mouseY);
		}
		
		checkClicks();
	}

}
