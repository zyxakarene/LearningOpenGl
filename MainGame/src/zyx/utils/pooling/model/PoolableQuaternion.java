package zyx.utils.pooling.model;

import org.lwjgl.util.vector.Quaternion;
import zyx.utils.pooling.IPoolable;

public class PoolableQuaternion extends Quaternion implements IPoolable
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
		w = 1;
	}

	@Override
	public void release()
	{
	}
}
