package zyx.opengl.textures.custom;

import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.opengl.PNGImageData;

class PngTextureLoader
{

	private static final PNGImageData LOADER = new PNGImageData();
	private static final LoadedTextureContainer CONTAINER = new LoadedTextureContainer();

	static LoadedTextureContainer getImageDataFor(InputStream input)
	{
		try
		{
			LOADER.loadImage(input);

			CONTAINER.data = LOADER.getImageBufferData();
			CONTAINER.width = LOADER.getWidth();
			CONTAINER.height = LOADER.getHeight();
			CONTAINER.hasAlpha = LOADER.hasAlpha();

			return CONTAINER;
		}
		catch (IOException ex)
		{
			String msg = "Error with loading texture";
			throw new RuntimeException(msg, ex);
		}
	}
}
