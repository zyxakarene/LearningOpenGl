package zyx.opengl.textures;

import zyx.utils.geometry.Rectangle;

public class SubTexture extends GameTexture
{

	public SubTexture(AbstractTexture parent, Rectangle rect, String name)
	{
		super(parent.getGlTexture(), rect, name, parent.slot);
	}
	
	@Override
	protected void onDispose()
	{
		texture = null;
	}
}
