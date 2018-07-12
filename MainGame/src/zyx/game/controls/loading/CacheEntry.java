package zyx.game.controls.loading;

import zyx.utils.interfaces.IDisposeable;

class CacheEntry<T extends IDisposeable> implements IDisposeable
{
	T model;
	int count;

	public CacheEntry(T model)
	{
		this.model = model;
		count = 1;
	}		

	@Override
	public void dispose()
	{
		model.dispose();
		model = null;
	}
}
