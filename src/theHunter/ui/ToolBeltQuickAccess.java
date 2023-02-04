package theHunter.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import luna2d.ResourceHandler;
import luna2d.Scene;
import luna2d.ui.UIGrid;
import theHunter.ObjectTypes;
import theHunter.Player;

public class ToolBeltQuickAccess extends UIGrid
{

	private ObjectTypes[] history;
	private ObjectTypes[] slots;
	
	public ToolBeltQuickAccess(Scene inScene, int x, int y, int rows, int columns, int cellSize, Color gridColor,
			Color bkgColor, int scale, int depth, int[][] fillTypes) 
	{
		super(inScene, x, y, rows, columns, cellSize, gridColor, bkgColor, scale, depth, fillTypes);
		
		this.slots = new ObjectTypes[ToolBelt.SLOTS];
		this.history = new ObjectTypes[ToolBelt.SLOTS];
	}
	
	public void attachToQuickAccess(ObjectTypes type, int slot)
	{
		this.handleNewItems(type);
		if (slot < ToolBelt.SLOTS && slot >= 0)
		{
			slots[slot] = type;
		}
	}
	
	public void removeFromQuickAccess(int slot)
	{
		this.handleRemovedItems(this.slots[slot]);
		this.history[slot] = slots[slot];
		this.slots[slot] = ObjectTypes.Empty;
	}
	
	private void handleNewItems(ObjectTypes type)
	{
		Player player = (Player)this.getScene().getPlayer();
		
		switch(type)
		{
		case Bush:
			break;
		case Empty:
			break;
		case FenceBottomLeft:
			break;
		case FenceBottomRight:
			break;
		case FenceHorz:
			break;
		case FenceTopLeft:
			break;
		case FenceTopRight:
			break;
		case FenceVert:
			break;
		case Fire:
			break;
		case GndDirt:
			break;
		case GndGrass:
			break;
		case GndRock:
			break;
		case GndWater:
			break;
		case GrowingBerryBush:
			break;
		case InvBerries:
			break;
		case InvFence:
			break;
		case InvFire:
			break;
		case InvRock:
			break;
		case InvTorch:
			player.heldTorch.visible = true;
			break;
		case Player:
			break;
		case Rabbit:
			break;
		case Rock:
			break;
		case ThrownRock:
			break;
		case Torch:
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
	
	private void handleRemovedItems(ObjectTypes type)
	{
		Player player = (Player)this.getScene().getPlayer();
		
		switch(type)
		{
		case Bush:
			break;
		case Empty:
			break;
		case FenceBottomLeft:
			break;
		case FenceBottomRight:
			break;
		case FenceHorz:
			break;
		case FenceTopLeft:
			break;
		case FenceTopRight:
			break;
		case FenceVert:
			break;
		case Fire:
			break;
		case GndDirt:
			break;
		case GndGrass:
			break;
		case GndRock:
			break;
		case GndWater:
			break;
		case GrowingBerryBush:
			break;
		case InvBerries:
			break;
		case InvFence:
			break;
		case InvFire:
			break;
		case InvRock:
			break;
		case InvTorch:
			player.heldTorch.visible = false;
			break;
		case Player:
			break;
		case Rabbit:
			break;
		case Rock:
			break;
		case ThrownRock:
			break;
		case Torch:
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
	
	public int getNextSlot()
	{
		for (int i = 0; i < ToolBelt.SLOTS; i++)
		{
			if (this.slots[i] == null || this.slots[i] == ObjectTypes.Empty)
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public boolean hasItem(ObjectTypes type)
	{
		for (int i = 0; i < ToolBelt.SLOTS; i++)
		{
			if (this.slots[i] != null && this.slots[i] == type)
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void render(Graphics g) 
	{
		if (!this.visible) return;
		if (this.customRender) return;
			
		super.renderBackground(g);

		for(int col = 0; col < columns; col++)
		{
			int cx = this.x + col * cellSize * this.scale;
			int cy = this.y;
			
			if (slots != null && slots[col] != null)
			{
				String imgName = ObjectTypes.values()[slots[col].intValue].imgName;
				BufferedImage img = ResourceHandler.getImage(imgName);
				g.drawImage(img, 
						cx, cy, cx + Math.round(cellSize * this.scale), cy + Math.round(cellSize * this.scale), 
						0, 0, img.getWidth(), img.getHeight(), null);					
			}
		}
		
		super.renderGrid(g);
	}

}
