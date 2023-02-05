package theHunter.objects.animals;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Timer;

import luna2d.GameObject;
import luna2d.Scene;
import luna2d.Vector2;
import luna2d.maps.WorldPosition;
import luna2d.renderables.Sprite;
import luna2d.timers.AnimalTask;
import theHunter.ObjectTypes;
import theHunter.TheHunter;

public abstract class Animal extends GameObject
{
	protected float hunger, temperature, anger;
	protected int age;
	
	public enum ANIMAL_STATES
	{
		Wondering,
		Chasing,
		Fleeing
	};
	
	protected GameObject chasingTarget;
	protected GameObject fleeingFrom;
	
	protected ANIMAL_STATES currentState;
	protected Sprite sprite;
	
	private AnimalTask lookTask;
	private AnimalTask chaseTask;
	private AnimalTask fleeTask;
	
	private Timer lookTimer;
	private Timer chaseTimer;
	private Timer fleeTimer;
	
	LinkedList<WorldPosition> path;
	int pathIndex = 0;
	
	protected boolean active;
	
	public Animal(Scene inScene, int worldX, int worldY, ObjectTypes type, String spriteImage) 
	{
		super(worldX, worldY, type.intValue, false, inScene);
		
		this.sprite = new Sprite(inScene, spriteImage, worldX, worldY, 1, TheHunter.ANIMAL_DRAW_LAYER);
		this.currentState = ANIMAL_STATES.Wondering;
		this.chasingTarget = null;
		this.fleeingFrom = null;
		
		this.path = new LinkedList<WorldPosition>();
		
		this.active = false;
	}
	
	public void init(ANIMAL_STATES startState, int lookDelay, int chaseDelay, int fleeDelay)
	{
		this.active = true;
		this.currentState = startState;
		
		lookTimer = new Timer();
		chaseTimer = new Timer();
		fleeTimer = new Timer();
		
		lookTask = new AnimalTask(this) 
		{
			@Override
			public void run()
			{
				this.animal.onWonderLooking();
			}
		};
		
		chaseTask = new AnimalTask(this) 
		{
			@Override
			public void run()
			{
				this.animal.onChaseUpdate();
			}
		};
		
		fleeTask = new AnimalTask(this) 
		{
			@Override
			public void run()
			{
				this.animal.onFleeUpdate();
			}
		};
		
		if (lookDelay > 0)
		{
			this.lookTimer.scheduleAtFixedRate(lookTask, 0, lookDelay);			
		}
			
		if (chaseDelay > 0)
		{			
			this.chaseTimer.scheduleAtFixedRate(chaseTask, chaseDelay, chaseDelay);
		}
		
		if (fleeDelay > 0)
		{
			this.fleeTimer.scheduleAtFixedRate(fleeTask, fleeDelay, fleeDelay);			
		}
	}
	
	// Called every tick of wondering timer
	protected abstract void onWonderLooking();
	
	// Called every tick of chase timer
	protected abstract void onChaseUpdate();
	
	// Called every tick of flee timer
	protected abstract void onFleeUpdate();
	
	@Override
	public void update()
	{
		if (!this.active) return;
		
		this.onUpdate();
		
		switch(this.currentState)
		{
		case Chasing:
			this.chase();
			break;
		case Fleeing:
			this.flee();
			break;
		case Wondering:
			this.wonder();
			break;
		default:
			this.currentState = ANIMAL_STATES.Wondering;
			this.wonder();
			break;
		}
	}
	
	@Override
	public void updateWorldPosition(WorldPosition wp)
	{
		super.updateWorldPosition(wp);
		this.sprite.updateWorldPosition(wp);
	}

	// Update method for wondering
	protected abstract void wonder();
	
	// Update method for chasing
	protected abstract void chase();
	
	// Update method for fleeing
	protected abstract void flee();
	
	// General update
	protected abstract void onUpdate();

	protected abstract void onMouseClick(MouseEvent e);
	protected abstract void onMousePressed(MouseEvent e);
	protected abstract void onMouseReleased(MouseEvent e);
	
	protected abstract void pauseTick();
	
	public abstract void onDestroy();

}
