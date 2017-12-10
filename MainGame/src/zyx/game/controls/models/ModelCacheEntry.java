package zyx.game.controls.models;

import zyx.opengl.models.implementations.WorldModel;
import zyx.utils.interfaces.IDisposeable;

class ModelCacheEntry implements IDisposeable
{
	WorldModel model;
	int count;

	public ModelCacheEntry(WorldModel model)
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
