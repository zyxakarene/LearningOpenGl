package zyx.engine.components.world.physics;

class Collision
{

	boolean x, y, z;

	boolean hasCollision()
	{
		return x && y && z;
	}

	@Override
	public String toString()
	{
		return "Collision{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}

}
