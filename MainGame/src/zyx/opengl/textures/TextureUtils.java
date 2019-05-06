package zyx.opengl.textures;

import zyx.opengl.textures.enums.TextureSlot;
import java.io.InputStream;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL13.glActiveTexture;
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
			
			TextureBinder.unbindTextures();
			
			return texture;
		}
		catch (Exception e)
		{
			Msg.error("Failed to load texture", e);
			throw new RuntimeException(e);
		}
	}
	
	static void activateTextureSlot(TextureSlot slot)
	{
		glActiveTexture(slot.glSlot);
	}
}
