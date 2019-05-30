package zyx.engine.components.cubemaps;

import zyx.engine.components.world.WorldObject;
import zyx.utils.pooling.IPoolable;

public class CubemapEntry implements IPoolable
{

	IReflective object;
	boolean dirty;

	@Override
	public void initialize(Object[] args)
	{
		dirty = true;
	}

	@Override
	public void reset()
	{
		dirty = true;
	}

	@Override
	public void release()
	{
		object = null;
		dirty = false;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof WorldObject && object.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return object.hashCode();
	}

}
