package theHunter.rangedWeapons;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.GameObject;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.maps.WorldPosition;
import luna2d.renderables.Sprite;
import theHunter.ObjectTypes;
import theHunter.TheHunter;

public class RangedWeapon extends GameObject
{

	private Sprite sprite;
		
	public RangedWeapon(Scene inScene, String imgName, int x, int y, int scale, int depth, float velX, float velY, float friction)
	{
		super(x, y, ObjectTypes.ThrownRock.intValue, false, inScene);
		this.sprite = new Sprite(inScene, imgName, x, y, scale, depth);
		this.setVelocity(velX, velY, friction);
		
		Vector2 gPos = Maths.convertToGrid(this.getWorldX(), this.getWorldY(), TheHunter.CELL_SIZE * Game.CAMERA_SCALE);
		WorldPosition pWP = Maths.convertToWorldPosition(gPos, Game.CAMERA_SCALE, TheHunter.ROWS, TheHunter.COLUMNS);

		this.sprite.updateWorldPosition(pWP);
		this.updateWorldPosition(pWP);
		
		this.getScene().getObjectHandler().addObject(this);
	}

	@Override
	protected void render(Graphics g) 
	{
	}

	@Override
	protected void update() 
	{
		if (this.sprite != null)
		{			
			this.sprite.updateWorldPosition(this.getWorldX(), this.getWorldY());		
		}
	}

	@Override
	protected void onMouseClick(MouseEvent e) 
	{
		this.mouseClicked = this.sprite.mouseClicked;
	}

	@Override
	protected void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	protected void onMouseReleased(MouseEvent e) 
	{
	}

	@Override
	public void onDestroy() 
	{
		this.sprite.destroy();
	}

	@Override
	protected void pauseTick() 
	{
	}
	
}
