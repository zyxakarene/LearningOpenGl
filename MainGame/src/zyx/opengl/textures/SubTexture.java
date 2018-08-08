package zyx.opengl.textures;

import zyx.utils.geometry.Rectangle;

public class SubTexture extends GameTexture
{

	public SubTexture(GameTexture parent, Rectangle rect, String name)
	{
		super(parent.texture, rect, name);
	}
	
	@Override
	protected void onDispose()
	{
		texture = null;
	}
}
