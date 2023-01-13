package theHunter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import luna2d.GameObject;
import luna2d.Scene;
import luna2d.renderables.FillBar;

public class Player extends GameObject
{

	private int moveSpeed = 5;
	private int size = 16;
	private int health = 100;
	
	private FillBar healthBar;
	
	public Player(int x, int y, int objectType, boolean inputEnabled, Scene inScene) 
	{
		super(x, y, objectType, inputEnabled, inScene);
		
		healthBar = new FillBar(this.health, this.x, this.y, this.size * 2, 4, 2, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
	}

	@Override
	protected void render(Graphics g) {
		
		g.setColor(Color.red);
		g.fillRect(x, y, this.size, this.size);
		
		healthBar.render(g);
	}

	@Override
	protected void update() 
	{
		
		if (this.isKeyPressed(KeyEvent.VK_W) || this.isKeyPressed(KeyEvent.VK_UP))
		{
			this.y -= moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_S) || this.isKeyPressed(KeyEvent.VK_DOWN))
		{
			this.y += moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_A) || this.isKeyPressed(KeyEvent.VK_LEFT))
		{
			this.x -= moveSpeed;
		}
		
		if (this.isKeyPressed(KeyEvent.VK_D) || this.isKeyPressed(KeyEvent.VK_RIGHT))
		{
			this.x += moveSpeed;
		}
		
		healthBar.updatePosition(this.x - this.size / 2,  this.y - this.size / 2);
		healthBar.setValue(this.health);
	}

}
