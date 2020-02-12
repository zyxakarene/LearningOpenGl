package zyx.engine.resources.impl.textures;

import zyx.engine.resources.impl.textures.TextureResource;
import zyx.opengl.textures.enums.TextureSlot;

public class NormalTextureResource extends TextureResource
{

	public NormalTextureResource(String path)
	{
		super(path);
	}

	@Override
	protected TextureSlot getTextureSlot()
	{
		return TextureSlot.WORLD_NORMAL;
	}
	
	@Override
	public String getResourceIcon()
	{
		return "normal.png";
	}
}
