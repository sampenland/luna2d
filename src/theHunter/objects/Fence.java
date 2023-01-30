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
	
	private int[] rotations = {0, 3, 6, 7, 8, 9};
	private int rotationIndex = 0;
	
	public Fence(Scene inScene) 
	{
		super(inScene, "Fence", 0, 0, 1, TheHunter.ENVIRONMENT_DRAW_LAYER, 16, 10, 0);
		this.enableCulling = false;
		this.setFixedScreenPosition(true);
		this.placing = true;
		this.mouseEnabled = true;
	}
	
	public void placeOnGround(int x, int y)
	{
		this.placing = false;
		this.enableCulling = true;
		this.setFixedScreenPosition(false);
		this.updateWorldPosition(x, y);
	}
	
	public void placeOnGround()
	{
		Vector2 gPos = Maths.convertToGrid(Game.mouseWorldX, Game.mouseWorldY, TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
		
		int x = gPos.x * 16;
		int y = gPos.y * 16;
		
		this.placeOnGround(x, y);
	}
	
	public void pickup()
	{
		this.placing = true;
		this.setFixedScreenPosition(true);
	}
	
	public void rotate()
	{
		this.rotationIndex++;
		if (this.rotationIndex >= this.rotations.length) 
		{
			this.rotationIndex = 0;
		}
		
		int frame = this.rotations[this.rotationIndex];
		
		this.setFrame(frame, false);
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
