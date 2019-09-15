package zyx.engine.resources.impl;

import zyx.opengl.buffers.DrawingRenderer;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;

public class DrawableTextureResource extends Resource
{

	private TextureFromInt drawTexture;

	public DrawableTextureResource(String path)
	{
		super(path);
	}

	@Override
	public AbstractTexture getContent()
	{
		return drawTexture;
	}

	@Override
	protected void onBeginLoad()
	{
		int id = DrawingRenderer.getInstance().underlayInt();
		drawTexture = new TextureFromInt(256, 256, id, TextureSlot.SHARED_DIFFUSE);
		
		onContentLoaded(drawTexture);
	}
	
	@Override
	void onDispose()
	{
		if(drawTexture != null)
		{
			drawTexture.dispose();
			drawTexture = null;
		}
	}
}
