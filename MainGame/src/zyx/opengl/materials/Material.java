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
	
	public RenderQueue queue;
	public int priority;
	
	public Shader shaderType;
	public AbstractShader shader;
	
	public StencilMode stencilMode;
	public StencilLayer stencilLayer;
	
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
		queue = RenderQueue.OPAQUE;
		
		stencilMode = StencilMode.NOTHING;
		stencilLayer = StencilLayer.NOTHING;
	}
	
	public void bind()
	{
		AbstractShader activeShader = getActiveShader();
		activeShader.bind();
		activeShader.upload();
		
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
		
		stencilMode.invoke(stencilLayer);
	}
	
	public Material cloneMaterial()
	{
		Material clone = createInstance();
		clone.zWrite = zWrite;
		clone.zTest = zTest;
		clone.culling = culling;
		clone.blend = blend;
		
		int textureCount = textures.length;
		System.arraycopy(textures, 0, clone.textures, 0, textureCount);
		
		return clone;
	}
	
	protected AbstractShader getActiveShader()
	{
		return shader;
	}

	protected abstract int getTextureCount();
	protected abstract Material createInstance();
	
	public void copyFrom(Material source)
	{
		this.zWrite = source.zWrite;
		this.zTest = source.zTest;
		this.culling = source.culling;
		this.blend = source.blend;
		this.queue = source.queue;
		this.priority = source.priority;
		this.shaderType = source.shaderType;
		this.shader = source.shader;
		this.stencilMode = source.stencilMode;
		this.stencilLayer = source.stencilLayer;
		
		System.arraycopy(source.textures, 0, textures, 0, textures.length);
	}
}
