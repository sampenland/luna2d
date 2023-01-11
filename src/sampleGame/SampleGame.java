package sampleGame;

import java.awt.Color;

import luna2d.Game;

public class SampleGame extends Game 
{

	private static final long serialVersionUID = -8234717309381689045L;

	private static final int WIDTH = 800, HEIGHT = 640;
		
	public static void main(String[] args)
	{
		Game g = new Game();
		g.init(WIDTH, HEIGHT, "Sample Game", Color.black);
		
		Player player = new Player(100, 100, 0, true, g.objHandler);
	}

	@Override
	public void run() 
	{

	}
	
}
