package zyx.opengl.materials.impl;

import zyx.opengl.buffers.DepthRenderer;
import zyx.opengl.materials.Material;
import zyx.opengl.materials.RenderQueue;
import zyx.opengl.shaders.AbstractShader;
import zyx.opengl.shaders.ShaderManager;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
import zyx.opengl.textures.TextureFromInt;
import zyx.opengl.textures.enums.TextureSlot;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;

public class WorldModelMaterial extends Material
{
	public static final byte DRAW_CASCADE_1 = 1;
	public static final byte DRAW_CASCADE_2 = 2;
	public static final byte DRAW_CASCADE_3 = 4;
	public static final byte DRAW_CASCADE_4 = 8;
	
	public byte activeShadowCascades;
	public boolean castsShadows;
	
	public Shader forwardShaderType;
	public AbstractShader forwardShader;
	
	public WorldModelMaterial(Shader shader)
	{
		super(shader);
		
		switch(shaderType)
		{
			case WORLD_1:
				forwardShaderType = Shader.WORLD_FORWARD_1;
				break;
			case WORLD_2:
				forwardShaderType = Shader.WORLD_FORWARD_2;
				break;
			case WORLD_3:
				forwardShaderType = Shader.WORLD_FORWARD_3;
				break;
			case WORLD_4:
				forwardShaderType = Shader.WORLD_FORWARD_4;
				break;
		}
		
		if (forwardShaderType != null)
		{
			forwardShader = ShaderManager.getInstance().get(forwardShaderType);
		}	
	}

	@Override
	protected AbstractShader getActiveShader()
	{
		if (queue == RenderQueue.OPAQUE)
		{
			return shader;
		}
		else
		{
			return forwardShader;
		}
	}
	
	@Override
	protected void setDefaultValues()
	{
		super.setDefaultValues();
		
		queue = RenderQueue.OPAQUE;
		castsShadows = true;
		
		int shadowInt = DepthRenderer.getInstance().depthInt();
		textures[TextureSlot.WORLD_SHADOW.index] = new TextureFromInt(64, 64, shadowInt, TextureSlot.WORLD_SHADOW);
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
		return 4;
	}

	@Override
	protected Material createInstance()
	{
		return new WorldModelMaterial(shaderType);
	}

	public void setShadowDistance(float distance)
	{
		activeShadowCascades = 0;
		
		if (queue == RenderQueue.TRANSPARENT)
		{
			return;
		}
		
		distance = FloatMath.abs(distance);
		
		if (distance >= GameConstants.CASCADE_MIN_DISTANCE_1 && distance <= GameConstants.CASCADE_MAX_DISTANCE_4)
		{
			if (distance > GameConstants.CASCADE_MIN_DISTANCE_1 && distance <= GameConstants.CASCADE_MAX_DISTANCE_1)
			{
				activeShadowCascades |= DRAW_CASCADE_1;
			}
			if (distance > GameConstants.CASCADE_MIN_DISTANCE_2 && distance <= GameConstants.CASCADE_MAX_DISTANCE_2)
			{
				activeShadowCascades |= DRAW_CASCADE_2;
			}
			if (distance > GameConstants.CASCADE_MIN_DISTANCE_3 && distance <= GameConstants.CASCADE_MAX_DISTANCE_3)
			{
				activeShadowCascades |= DRAW_CASCADE_3;
			}
			if (distance > GameConstants.CASCADE_MIN_DISTANCE_4 && distance <= GameConstants.CASCADE_MAX_DISTANCE_4)
			{
				activeShadowCascades |= DRAW_CASCADE_4;
			}
		}
	}
}
