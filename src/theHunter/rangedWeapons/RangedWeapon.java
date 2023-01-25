package theHunter.rangedWeapons;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import luna2d.GameObject;
import luna2d.Log;
import luna2d.Scene;
import luna2d.renderables.Sprite;
import theHunter.ObjectTypes;

public class RangedWeapon extends GameObject
{

	private Sprite sprite;
		
	public RangedWeapon(Scene inScene, String imgName, int x, int y, int scale, int depth, float velX, float velY, float friction)
	{
		super(x, y, ObjectTypes.ThrownRock.intValue, false, inScene);
		this.sprite = new Sprite(inScene, imgName, x, y, scale, depth);
		this.setVelocity(velX, velY, friction);
		this.getScene().getObjectHandler().addObject(this);
	}

	@Override
	protected void render(Graphics g) {
		// TODO Auto-generated method stub
		
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
	protected void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() 
	{
		this.sprite.destroy();
	}
	
}
