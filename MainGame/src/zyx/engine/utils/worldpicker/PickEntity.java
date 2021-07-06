package zyx.engine.utils.worldpicker;

import java.util.ArrayList;
import zyx.engine.components.world.WorldObject;
import zyx.utils.interfaces.IPhysbox;

class PickEntity
{

	IPhysbox target;

	private ArrayList<IWorldPickedItem> callbacks;
	private ArrayList<Integer> maxDistances;

	public PickEntity(IPhysbox target)
	{
		this.target = target;
		this.callbacks = new ArrayList<>();
		maxDistances = new ArrayList<>();
	}

	void onGeometryPicked(ClickedInfo info)
	{
		int distance = (int) info.distance;

		int len = callbacks.size();
		for (int i = 0; i < len; i++)
		{
			int maxDistance = maxDistances.get(i);
			if (distance <= maxDistance)
			{
				IWorldPickedItem clickCallback = callbacks.get(i);
				clickCallback.onGeometryPicked(info);
			}
		}
	}

	void add(IWorldPickedItem clickCallback, int maxDistance)
	{
		callbacks.add(clickCallback);
		maxDistances.add(maxDistance);
	}

	void remove(IWorldPickedItem clickCallback)
	{
		int index = callbacks.indexOf(clickCallback);
		if (index >= 0)
		{
			callbacks.remove(index);
			maxDistances.remove(index);
		}
	}

	boolean isEmpty()
	{
		return callbacks.isEmpty();
	}

	void dispose()
	{
		if (callbacks != null)
		{
			callbacks.clear();
			callbacks = null;
		}

		if (maxDistances != null)
		{
			maxDistances.clear();
			maxDistances = null;
		}

		target = null;
	}

	WorldObject targetAsWorldObject()
	{
		if (target instanceof WorldObject)
		{
			return (WorldObject) target;
		}
		
		return null;
	}

}
