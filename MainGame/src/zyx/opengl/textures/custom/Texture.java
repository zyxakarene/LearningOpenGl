package zyx.opengl.textures.custom;

import java.io.InputStream;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import zyx.opengl.textures.enums.TextureFiltering;

public class Texture implements ITexture
{

	private int textureId;

	private int width;
	private int height;

	public Texture(InputStream content)
	{
		this(content, TextureFiltering.NEAREST);
	}

	public Texture(InputStream content, TextureFiltering filter)
	{
		this.textureId = GL11.glGenTextures();
		
		setData(content);
		setFiltering(filter);
	}

	public Texture(int textureId, InputStream content, TextureFiltering filter)
	{
		this.textureId = textureId;
		
		setData(content);
		setFiltering(filter);
	}

	public void setData(InputStream content)
	{
		LoadedTextureContainer textureContainer = PngTextureLoader.getImageDataFor(content);

		width = textureContainer.width;
		height = textureContainer.height;

		int format = textureContainer.hasAlpha ? GL11.GL_RGBA : GL11.GL_RGB;

		glBindTexture(GL_TEXTURE_2D, textureId);
		glTexImage2D(GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, format, GL11.GL_UNSIGNED_BYTE, textureContainer.data);
	}

	private void setFiltering(TextureFiltering filter)
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter.glId);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter.glId);
	}

	@Override
	public int getWidth()
	{
		return width;
	}

	@Override
	public int getHeight()
	{
		return height;
	}

	@Override
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, textureId);
	}

	@Override
	public void dispose()
	{
		GL11.glDeleteTextures(textureId);
		textureId = -1;
	}

}
