package zyx.opengl.materials.impl;

import zyx.opengl.materials.Material;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.enums.TextureSlot;

public class WorldObjectMaterial extends Material
{

	public WorldObjectMaterial(Shader shader)
	{
		super(shader);
	}
	
	public WorldObjectMaterial(AbstractShader shader)
	{
		super(shader);
	}
	
	public AbstractTexture getDiffuse()
	{
		return textures[TextureSlot.SHARED_DIFFUSE.index];
	}
	
	public AbstractTexture getNormal()
	{
		return textures[TextureSlot.WORLD_NORMAL.index];
	}
	
	public AbstractTexture getSpecular()
	{
		return textures[TextureSlot.WORLD_SPECULAR.index];
	}
	
	public void setDiffuse(AbstractTexture texture)
	{
		textures[TextureSlot.SHARED_DIFFUSE.index] = texture;
	}
	
	public void setNormal(AbstractTexture texture)
	{
		textures[TextureSlot.WORLD_NORMAL.index] = texture;
	}
	
	public void setSpecular(AbstractTexture texture)
	{
		textures[TextureSlot.WORLD_SPECULAR.index] = texture;
	}

	@Override
	protected int getTextureCount()
	{
		return 3;
	}

	@Override
	protected Material createInstance()
	{
		return new WorldObjectMaterial(shader);
	}

}
