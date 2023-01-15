package theHunter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.GameObject;
import luna2d.Scene;
import luna2d.renderables.FillBar;
import luna2d.renderables.Sprite;

public class Player extends GameObject
{

	private int moveSpeed = 5;
	private int size = 16;
	private int health = 100;
	
	private FillBar healthBar;
	private Sprite sprite;
	
	public Player(int x, int y, Scene inScene) 
	{
		super(x, y, ObjectTypes.Player.intValue, true, inScene);
		
		sprite = new Sprite(inScene, "Player", x, y, 1.0f, 16, 4, 250);
		healthBar = new FillBar(this.health, this.x, this.y, this.size * 2, 4, 2, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
	}

	@Override
	protected void render(Graphics g) 
	{	
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
		
		this.sprite.updatePosition(this.x, this.y);
	}

	@Override
	protected void onMouseClick(MouseEvent e) 
	{
	}

	@Override
	protected void onMousePressed(MouseEvent e) 
	{
	}

	@Override
	protected void onMouseReleased(MouseEvent e) 
	{	
	}

}
