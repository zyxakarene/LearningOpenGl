package zyx.opengl.textures;

import zyx.opengl.textures.enums.TextureSlot;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TextureBinder
{

	private static AbstractTexture[] activeTextures = new AbstractTexture[TextureSlot.COUNT];

	static void bind(AbstractTexture texture)
	{
		int index = texture.slot.index;
		if (activeTextures[index] != texture)
		{
			activateTextureSlot(texture.slot);
			
			activeTextures[index] = texture;
			texture.onBind();
		}
	}

	public static void unbindTextures()
	{
		for (int i = 0; i < activeTextures.length; i++)
		{
			AbstractTexture texture = activeTextures[i];
			if (texture != null)
			{
				activateTextureSlot(texture.slot);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				
				activeTextures[i] = null;
			}
		}
	}
	
	private static void activateTextureSlot(TextureSlot slot)
	{
		glActiveTexture(slot.glSlot);
	}
	
	static void unbindTexture(AbstractTexture texture)
	{
		int index = texture.slot.index;
		if (activeTextures[index] == texture)
		{
			activeTextures[index] = null;
			
			activateTextureSlot(texture.slot);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		}
	}

	public static String currentTexture()
	{
		StringBuilder builder = new StringBuilder();
		
		for (AbstractTexture texture : activeTextures)
		{
			if (texture != null)
			{
				builder.append(texture);
				builder.append(" ");
			}
		}

		if (builder.length() == 0)
		{
			return "[No current texture]";
		}
		else
		{
			return builder.toString();
		}
	}
}
