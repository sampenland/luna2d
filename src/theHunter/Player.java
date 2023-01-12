package theHunter;

import java.awt.Color;
import java.awt.Graphics;

import luna2d.GameObject;
import luna2d.Scene;

public class Player extends GameObject
{

	private int moveSpeed = 5;
	private int size = 16;
	
	public Player(int x, int y, int objectType, boolean inputEnabled, Scene inScene) 
	{
		super(x, y, objectType, inputEnabled, inScene);
	}

	@Override
	protected void render(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(x, y, this.size, this.size);
		
	}

	@Override
	protected void update() 
	{
		
		if (this.isKeyPressed((int)'W'))
		{
			this.y -= moveSpeed;
		}
		
		if (this.isKeyPressed((int)'S'))
		{
			this.y += moveSpeed;
		}
		
		if (this.isKeyPressed((int)'A'))
		{
			this.x -= moveSpeed;
		}
		
		if (this.isKeyPressed((int)'D'))
		{
			this.x += moveSpeed;
		}
		
		
	}

}
