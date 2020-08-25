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
	
	public final AbstractShader shader;
	
	protected final AbstractTexture[] textures;

	public Material(AbstractShader shader)
	{
		this.shader = shader;
		
		int textureCount = getTextureCount();
		textures = new AbstractTexture[textureCount];
		
		zWrite = ZWrite.ENABLED;
		zTest = ZTest.LESS;
		culling = Culling.BACK;
		blend = BlendMode.NORMAL;
	}
	
	public Material(Shader shader)
	{
		this(ShaderManager.getInstance().get(shader));
	}
	
	public void bind()
	{
		shader.bind();
		
		for (AbstractTexture texture : textures)
		{
			texture.bind();
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
		
		return clone;
	}

	protected abstract int getTextureCount();
	protected abstract Material createInstance();
}
