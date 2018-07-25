package zyx.engine.resources.impl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.BitmapFontGenerator;

public class FontResource extends BaseTextureRequiredResource
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

			loadTexture(textureResource);
		}
		catch (IOException ex)
		{
			Logger.getLogger(FontResource.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	void onDispose()
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
	protected void onTextureLoaded(AbstractTexture texture)
	{
		generator.setMainTexture(texture);
		font = generator.createBitmapFont();
		
		onContentLoaded(font);
	}
}
