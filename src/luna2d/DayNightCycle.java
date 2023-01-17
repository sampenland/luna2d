package luna2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;

import luna2d.timers.DayNightCycleTask;

public class DayNightCycle 
{
	
	private Rectangle darknessRect;
	private int inGameMinutes, inGameHours;
	private long inGameDays;
	
	private DayNightCycleTask cycleTask;
	private Timer cycleTimer;
	
	private int startOfDayHour, startOfNightHour;
	private Color dawnColor, dayColor, dustColor, nightColor, currentColor;
	
	public DayNightCycle(int msOfInGameMinute, int startOfDayHour, int startOfNightHour, Color dawnColor, Color dayColor, Color dustColor, Color nightColor)
	{
		this.darknessRect = new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT);
		
		this.startOfDayHour = startOfDayHour;
		this.startOfNightHour = startOfNightHour;
		
		this.dawnColor = dawnColor;
		this.dayColor = dayColor;
		this.dustColor = dustColor;
		this.nightColor = nightColor;
		
		this.cycleTask = new DayNightCycleTask(this)
		{
			@Override
			public void run()
			{
				this.cycle.minuteTick();
			}
		};
		
		this.cycleTimer.schedule(cycleTask, msOfInGameMinute);
	}
	
	public void minuteTick()
	{
		this.inGameMinutes++;
		
		if(this.inGameMinutes > 60)
		{
			this.inGameMinutes = 0;
			this.inGameHours++;
			
			if (this.inGameHours > 24)
			{
				this.inGameHours = 0;
				this.inGameDays++;
			}
		}
	}
	
	public String getTime()
	{
		return this.inGameHours + ":" + this.inGameMinutes;
	}
	
	public String getDaysAndTime()
	{
		return this.inGameDays + " Days   " + this.inGameHours + ":" + this.inGameMinutes;
	}
	
	public int getMinutes()
	{
		return this.inGameMinutes;
	}
	
	public int getHours()
	{
		return this.inGameHours;
	}
	
	public long getDays()
	{
		return this.inGameDays;
	}
	
	public void render(Graphics g)
	{
		g.setColor(this.currentColor);
		g.drawRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
	
}
