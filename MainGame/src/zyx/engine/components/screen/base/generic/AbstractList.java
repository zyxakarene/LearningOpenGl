package zyx.engine.components.screen.base.generic;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;

abstract class AbstractList extends DisplayObjectContainer
{
	AbstractList()
	{
	}

	@Override
	protected void onChildAdded(DisplayObject child)
	{
		sortItems();
	}

	@Override
	protected void onChildRemoved(DisplayObject child)
	{
		sortItems();
	}

	protected abstract void sortItems();
	
}
