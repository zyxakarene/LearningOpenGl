package zyx.opengl.textures;

import java.io.InputStream;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import zyx.utils.GameConstants;
import zyx.utils.exceptions.Msg;

class TextureUtils
{

	static Texture createTexture(InputStream stream)
	{
		try
		{
			Texture texture = TextureLoader.getTexture(GameConstants.TEXTURE_FORMAT, stream, GL11.GL_NEAREST);
			
			TextureBinder.unbindTexture();
			
			return texture;
		}
		catch (Exception e)
		{
			Msg.error("Failed to load texture", e);
			throw new RuntimeException(e);
		}
	}
}
