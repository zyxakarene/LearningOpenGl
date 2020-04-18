package zyx.engine.utils.worldpicker;

import java.util.ArrayList;
import java.util.Objects;
import zyx.utils.interfaces.IPhysbox;

class PickEntity
{
	final IPhysbox target;
	final ArrayList<IWorldPickedItem> callbacks;

	public PickEntity(IPhysbox target)
	{
		this.target = target;
		this.callbacks = new ArrayList<>();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		
		if (obj instanceof IPhysbox)
		{
			return target == obj;
		}
		
		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hashCode(target);
	}

	void onGeometryPicked(ClickedInfo info)
	{
		for (IWorldPickedItem callback : callbacks)
		{
			callback.onGeometryPicked(info);
		}
	}
}
