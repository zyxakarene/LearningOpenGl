package zyx.engine.resources.impl;

import zyx.engine.resources.impl.sub.ISubResourceLoaded;
import zyx.engine.resources.impl.sub.BaseRequiredSubResource;
import zyx.engine.resources.impl.sub.SubResourceBatch;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.BitmapFontGenerator;

public class FontResource extends BaseRequiredSubResource implements ISubResourceLoaded<AbstractTexture>
{

	private BitmapFont font;
	private BitmapFontGenerator generator;

	public FontResource(String path)
	{
		super(path);
	}

	@Override
	public BitmapFont getContent()
	{
		return font;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream fontData)
	{
		try
		{
			String textureResource = fontData.readUTF();

			generator = new BitmapFontGenerator();
			generator.setFontData(fontData);

			addResourceBatch(new SubResourceBatch(this, textureResource));
		}
		catch (IOException ex)
		{
			Logger.getLogger(FontResource.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	protected void onDispose()
	{
		super.onDispose();

		if (font != null)
		{
			font.dispose();
			font = null;
		}

		if (generator != null)
		{
			generator.dispose();
			generator = null;
		}
	}

	@Override
	public void onLoaded(ArrayList<AbstractTexture> data)
	{
		generator.setMainTexture(data.get(0));
		font = generator.createBitmapFont();
	}
	
	@Override
	protected void onSubBatchesLoaded()
	{
		onContentLoaded(font);
	}
}
