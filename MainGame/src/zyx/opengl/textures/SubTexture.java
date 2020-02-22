package zyx.opengl.textures;

import zyx.utils.geometry.Rectangle;

public class SubTexture extends GameTexture
{

	public SubTexture(AbstractTexture parent, Rectangle rect, String name)
	{
		super(parent.getGlTexture(), rect, name, parent.slot);
	}
	
	public void refresh(AbstractTexture parent)
	{
		texture = parent.getGlTexture();
		setSizes();
	}
	
	@Override
	protected void onDispose()
	{
		texture = null;
	}
}
