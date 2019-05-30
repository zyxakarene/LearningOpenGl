package zyx.opengl.reflections;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.textures.CubemapArrayTexture;

public class Cubemap
{
	
	public Vector3f[] positions;
	public CubemapArrayTexture texture;

	public Cubemap(Vector3f[] positions, CubemapArrayTexture texture)
	{
		this.positions = positions;
		this.texture = texture;
	}

	public void dispose()
	{
		positions = null;
		
		if (texture != null)
		{
			texture.dispose();
			texture = null;
		}
	}

	public void bind()
	{
		if (texture != null)
		{
			texture.bind();
		}
	}
}
