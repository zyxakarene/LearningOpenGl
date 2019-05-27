package zyx.engine.resources.impl;

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
		return TextureSlot.SLOT_1;
	}
	
}
