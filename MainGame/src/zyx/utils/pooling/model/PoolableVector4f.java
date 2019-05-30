package zyx.utils.pooling.model;

import org.lwjgl.util.vector.Vector4f;
import zyx.utils.pooling.IPoolable;

public class PoolableVector4f extends Vector4f implements IPoolable
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
		w = 0;
	}

	@Override
	public void release()
	{

	}
}
