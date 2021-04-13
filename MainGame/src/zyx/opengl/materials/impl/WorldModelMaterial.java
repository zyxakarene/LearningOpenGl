package zyx.opengl.materials.impl;

import zyx.opengl.materials.Material;
import zyx.opengl.materials.RenderQueue;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;
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
	
	public WorldModelMaterial(Shader shader)
	{
		super(shader);
	}

	@Override
	protected void setDefaultValues()
	{
		super.setDefaultValues();
		
		queue = RenderQueue.GEOMETRY;
		castsShadows = true;
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
		return new WorldModelMaterial(shaderType);
	}

	public void setShadowDistance(float distance)
	{
		distance = FloatMath.abs(distance);
		
		activeShadowCascades = 0;
		
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
