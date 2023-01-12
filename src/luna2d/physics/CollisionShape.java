package luna2d.physics;

public abstract class CollisionShape 
{

	protected int x, y;	
	
	protected enum TYPES 
	{
		Box,
		Circle
	}
	
	protected TYPES shape;
	
	protected abstract boolean collidingWith(CollisionShape other);
	
}
