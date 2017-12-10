package zyx.utils.pooling.model;

import org.lwjgl.util.vector.Vector3f;
import zyx.utils.pooling.IPoolable;

public class PoolableVector3f extends Vector3f implements IPoolable
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
		z = 0;
	}

	@Override
	public void release()
	{

	}

	@Override
	public void dispose()
	{
	}

}
