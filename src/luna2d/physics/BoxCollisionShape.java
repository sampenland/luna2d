package luna2d.physics;

public class BoxCollisionShape extends CollisionShape
{
	
	private int w, h;
	
	public BoxCollisionShape(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	protected boolean collidingWith(CollisionShape other) 
	{		
		switch(other.shape)
		{
		
			// Collision between this box and another box
			case Box:				
				
				BoxCollisionShape oBox = (BoxCollisionShape)other;
				
				boolean widthIsPositive = Math.min(this.w, oBox.w) > Math.max(this.x, oBox.x);
		        boolean heightIsPositive = Math.min(this.h, oBox.h) > Math.max(this.y, oBox.y);
		        return ( widthIsPositive && heightIsPositive);
				
			// Collision between this box and a circle
			case Circle:
				
				return false;
		}
		
		return false;
		
	}
	
}
