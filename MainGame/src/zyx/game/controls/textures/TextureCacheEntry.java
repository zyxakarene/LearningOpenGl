package zyx.game.controls.textures;

import zyx.opengl.textures.GameTexture;
import zyx.utils.interfaces.IDisposeable;

class TextureCacheEntry implements IDisposeable
{
	GameTexture texture;
	int count;

	public TextureCacheEntry(GameTexture model)
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
