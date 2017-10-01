package zyx.opengl.textures;

import java.io.InputStream;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import zyx.utils.exceptions.Msg;

class TextureUtils
{

	static Texture createTexture(String name, String format)
	{
		try
		{
			String filename = String.format("assets/textures/%s.%s", name, format);
			InputStream stream = ResourceLoader.getResourceAsStream(filename);
			Texture texture = TextureLoader.getTexture(format, stream, GL11.GL_NEAREST);

			return texture;
		}
		catch (Exception e)
		{
			Msg.error("Failed to load texture", e);
			throw new RuntimeException(e);
		}
	}
}
