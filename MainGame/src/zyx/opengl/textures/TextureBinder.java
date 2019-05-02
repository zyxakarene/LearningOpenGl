package zyx.opengl.textures;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureImpl;

public class TextureBinder
{

	private static AbstractTexture[] activeTextures = new AbstractTexture[TextureSlot.COUNT];

	static void bind(AbstractTexture texture)
	{
		int index = texture.slot.index;
		if (activeTextures[index] != texture)
		{
			TextureUtils.activateTextureSlot(texture.slot);
			
			activeTextures[index] = texture;
			texture.onBind();
		}
	}

	public static void unbindTextures()
	{
		TextureImpl.unbind();
		
		for (int i = 0; i < activeTextures.length; i++)
		{
			AbstractTexture texture = activeTextures[i];
			if (texture != null)
			{
				TextureUtils.activateTextureSlot(texture.slot);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				
				activeTextures[i] = null;
			}
		}
	}
	
	static void dispose(AbstractTexture texture)
	{
		int index = texture.slot.index;
		if (activeTextures[index] == texture)
		{
			TextureImpl.unbind();
			activeTextures[index] = null;
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
