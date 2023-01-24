package theHunter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import luna2d.Game;
import luna2d.Maths;
import luna2d.Scene;
import luna2d.lights.GlowLight;
import luna2d.playerControllers.SimplePlayer;
import luna2d.renderables.FillBar;
import luna2d.renderables.TextDisplay;
import theHunter.inventoryItems.InvBerries;
import theHunter.ui.Backpack;

public class Player extends SimplePlayer
{
	private Backpack backpack;
	
	private float hunger;
	private float hungerDrain = 0.02f;
	
	private FillBar healthBar;
	private FillBar hungerBar;
	private GlowLight torch;
	
	private TextDisplay timeLabel;
	
	public Player(Scene inScene, String imageName, int x, int y, int scale, int cellSize, int frames,
			int msBetweenFrames) 
	{
		super(inScene, imageName, x, y, scale, cellSize, frames, msBetweenFrames);
		
		this.sprite.enableCulling = false;
		
		healthBar = new FillBar(Math.round(this.health), Game.WIDTH / 2 - cellSize * 2, Game.HEIGHT / 2 - cellSize * 2 - 12, 
				cellSize * 2, 4, 2, 1, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
		healthBar.setEnableCameraScaling(false);
		healthBar.setFixedScreenPosition(true);
		
		this.hunger = 100;
		new TextDisplay(inScene, "Hunger", 10, 35, Color.white);
		hungerBar = new FillBar(Math.round(this.hunger), 60, 30, cellSize * 3, 4, 2, 1, Color.GRAY, Color.WHITE, Color.GREEN, inScene);
		hungerBar.setEnableCameraScaling(false);
		hungerBar.setFixedScreenPosition(true);

		this.setZoomingEnabled(true);
		
		torch = new GlowLight(inScene, 0, 0, 2);
		
		this.timeLabel = new TextDisplay(inScene, inScene.getDaysAndTime(), 10, 20, Color.white);
		
		backpack = new Backpack(inScene);		
		backpack.show();
		
		this.inScene.setPlayer(this);
		
	}
	
	public void useItem(ObjectTypes type)
	{
		switch(type)
		{
		case Bush:
			break;
		case Empty:
			break;
		case GndDirt:
			break;
		case GndGrass:
			break;
		case GndRock:
			break;
		case GndWater:
			break;
		case InvBerries:
			this.eat(InvBerries.BERRY_HUNGER_SUBTRACTION);
			break;
		case Player:
			break;
		case Tree:
			break;
		case Water:
			break;
		case Wolf:
			break;
		default:
			break;
		
		}
	}
	
	public void eat(int hunger)
	{
		this.hunger += hunger;
		this.hunger = Maths.clamp(this.hunger, 100, 0);
	}
	
	public void pickup(ObjectTypes type, int amount)
	{
		InventoryItem item = null;
		
		switch(type)
		{
		case Bush:
			break;
		case Empty:
			break;
		case GndDirt:
			break;
		case GndGrass:
			break;
		case GndRock:
			break;
		case GndWater:
			break;
		case InvBerries:
			item = new InvBerries(this.inScene, amount);
			break;
		case Player:
			break;
		case Tree:
			break;
		case Water:
			break;
		case Wolf:
			break;
		default:
			break;
		}
		
		if (item == null) return;
		
		backpack.addToBackpack(item);
	}

	@Override
	protected void render(Graphics g) 
	{			
	}
	
	private void checkKeys()
	{
		if (this.isKeyPressed(KeyEvent.VK_TAB))
		{
			this.backpack.toggleVisible();
		}
	}

	@Override
	protected void update() 
	{
		super.update();
		checkKeys();
		Game.updatePlayerPosition(this.worldX, this.worldY, 200);
		
		this.timeLabel.updateText(this.inScene.getDaysAndTime());
		
		healthBar.setValue(Math.round(this.health));
		
		handleHunger();
		
		this.torch.updateScreenPosition(Game.WIDTH / 2 - this.torch.getWidth(), 
				Game.HEIGHT / 2 - this.torch.getHeight());
	}
	
	private void handleHunger()
	{
		float drain = this.hungerDrain; // also with conditions
		
		this.hunger -= drain;
		hungerBar.setValue(Math.round(this.hunger));
		
		if (this.hunger < 1)
		{
			this.health -= 0.05f;
		}
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
