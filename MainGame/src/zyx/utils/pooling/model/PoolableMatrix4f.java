package zyx.utils.pooling.model;

import org.lwjgl.util.vector.Matrix4f;
import zyx.utils.pooling.IPoolable;

public class PoolableMatrix4f extends Matrix4f implements IPoolable
{

	@Override
	public void initialize(Object[] args)
	{
	}

	@Override
	public void reset()
	{
		setIdentity();
	}

	@Override
	public void release()
	{
	}
}
