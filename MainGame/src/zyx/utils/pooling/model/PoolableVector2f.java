package zyx.utils.pooling.model;

import org.lwjgl.util.vector.Vector2f;
import zyx.utils.pooling.IPoolable;

public class PoolableVector2f extends Vector2f implements IPoolable
{

	@Override
	public void initialize(Object[] args)
	{
	}

	@Override
	public void reset()
	{
		x = 0;
		y = 0;
	}

	@Override
	public void release()
	{

	}
}
