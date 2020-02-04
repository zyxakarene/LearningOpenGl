package zyx.engine.resources.impl.textures;

import zyx.engine.resources.impl.textures.TextureResource;
import zyx.opengl.textures.enums.TextureSlot;

public class SpecularTextureResource extends TextureResource
{

	public SpecularTextureResource(String path)
	{
		super(path);
	}

	@Override
	protected TextureSlot getTextureSlot()
	{
		return TextureSlot.WORLD_SPECULAR;
	}
	
}
