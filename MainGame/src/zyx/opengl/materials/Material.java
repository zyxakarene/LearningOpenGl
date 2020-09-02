package zyx.opengl.materials;

import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public abstract class Material
{
	public ZWrite zWrite;
	public ZTest zTest;
	public Culling culling;
	public BlendMode blend;
	
	public Shader shaderType;
	public AbstractShader shader;
	
	protected final AbstractTexture[] textures;

	public Material(Shader shader)
	{
		this.shaderType = shader;
		this.shader = ShaderManager.getInstance().get(shader);
		
		int textureCount = getTextureCount();
		textures = new AbstractTexture[textureCount];
		
		setDefaultValues();
	}
	
	protected void setDefaultValues()
	{
		zWrite = ZWrite.ENABLED;
		zTest = ZTest.LESS;
		culling = Culling.BACK;
		blend = BlendMode.NORMAL;
	}
	
	public void bind()
	{
		shader.bind();
		
		for (AbstractTexture texture : textures)
		{
			if (texture != null)
			{
				texture.bind();
			}
		}
		
		zWrite.invoke();
		zTest.invoke();
		culling.invoke();
		blend.invoke();
	}
	
	public Material cloneMaterial()
	{
		Material clone = createInstance();
		clone.zWrite = zWrite;
		clone.zTest = zTest;
		clone.culling = culling;
		clone.blend = blend;
		
		int textureCount = textures.length;
		for (int i = 0; i < textureCount; i++)
		{
			clone.textures[i] = textures[i];
		}
		
		return clone;
	}

	protected abstract int getTextureCount();
	protected abstract Material createInstance();
}
