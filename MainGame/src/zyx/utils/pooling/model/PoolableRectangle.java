package zyx.utils.pooling.model;

import zyx.utils.geometry.Rectangle;
import zyx.utils.pooling.IPoolable;

public class PoolableRectangle extends Rectangle implements IPoolable
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
		width = 0;
		height = 0;
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
