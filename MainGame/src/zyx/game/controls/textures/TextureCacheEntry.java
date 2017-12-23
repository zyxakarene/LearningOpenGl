package zyx.game.controls.textures;

import zyx.opengl.textures.AbstractTexture;
import zyx.utils.interfaces.IDisposeable;

class TextureCacheEntry implements IDisposeable
{
	AbstractTexture texture;
	int count;

	public TextureCacheEntry(AbstractTexture model)
	{
		this.texture = model;
		count = 1;
	}		

	@Override
	public void dispose()
	{
		texture.dispose();
		texture = null;
	}
}
