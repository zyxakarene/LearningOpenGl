package zyx.engine.components.cubemaps;

import zyx.engine.components.world.WorldObject;
import zyx.utils.pooling.IPoolable;

public class CubemapEntry implements IPoolable
{

	WorldObject object;
	boolean dirty;
	int cubemapIndex;

	@Override
	public void initialize(Object[] args)
	{
		dirty = true;
		cubemapIndex = 0;
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
		cubemapIndex = 0;
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
