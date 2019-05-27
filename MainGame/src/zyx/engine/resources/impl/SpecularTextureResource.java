package zyx.engine.resources.impl;

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
		return TextureSlot.SLOT_2;
	}
	
}
