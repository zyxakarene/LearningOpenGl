package zyx.opengl.textures;

import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.geometry.Rectangle;

public class SubTexture extends GameTexture
{

	public SubTexture(GameTexture parent, Rectangle rect, String name)
	{
		super(parent.texture, rect, name, TextureSlot.SLOT_0);
	}
	
	@Override
	protected void onDispose()
	{
		texture = null;
	}
}
