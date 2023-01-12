package theHunter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

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
		
		if (this.isKeyPressed(KeyEvent.VK_W))
		{
			this.y -= moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_S))
		{
			this.y += moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_A))
		{
			this.x -= moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_D))
		{
			this.x += moveSpeed;
		}
		
		
	}

}
