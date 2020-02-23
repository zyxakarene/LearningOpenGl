package zyx.opengl.textures.custom;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.newdawn.slick.opengl.PNGImageData;
import zyx.utils.cheats.Print;

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
			Print.err("==== [ERROR] Failed to parse a texture file! ====");

			int w = 32;
			int h = 32;
			int bytePerPixel = 3;
			ByteBuffer buffer = ByteBuffer.allocateDirect(w * h * bytePerPixel);
			boolean black = true;
			for (int i = 0; i < w; i++)
			{
				for (int j = 0; j < h; j++)
				{
					if (j == 0)
					{
						black = !black;
					}
					
					if (black)
					{
						buffer.put((byte) 0x00);
						buffer.put((byte) 0x00);
						buffer.put((byte) 0x00);
					}
					else
					{
						buffer.put((byte) 0xFF);
						buffer.put((byte) 0x00);
						buffer.put((byte) 0xFF);
					}
					
					black = !black;
				}
			}
			buffer.rewind();

			CONTAINER.data = buffer;
			CONTAINER.width = w;
			CONTAINER.height = h;
			CONTAINER.hasAlpha = false;
			return CONTAINER;
		}

	}
}
