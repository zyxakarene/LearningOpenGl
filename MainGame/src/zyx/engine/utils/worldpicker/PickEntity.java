package zyx.engine.utils.worldpicker;

import java.util.ArrayList;
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

	void onGeometryPicked(ClickedInfo info)
	{
		for (IWorldPickedItem callback : callbacks)
		{
			callback.onGeometryPicked(info);
		}
	}
}
