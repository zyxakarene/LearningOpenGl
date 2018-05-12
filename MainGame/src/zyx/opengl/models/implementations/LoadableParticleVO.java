package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.textures.AbstractTexture;

public class LoadableParticleVO implements ILoadableVO
{

	int instanceCount = 10;
	
	Vector3f gravity = new Vector3f(0, 0, 0);
	
	Vector2f areaX = new Vector2f(0, 0);
	Vector2f areaY = new Vector2f(0, 0);
	Vector2f areaZ = new Vector2f(0, 0);
	
	float speed = 0;
	float speedVariance = 0;
	
	Vector4f startColor = new Vector4f(1, 1, 1, 1);
	Vector4f endColor = new Vector4f(1, 1, 1, 1);
	
	float startScale = 1;
	float endScale = 1;
	float scaleVariance = 0;
	
	float lifespan = 1000;
	float lifespanVariance = 0;
	
	String texture;
	AbstractTexture gameTexture;

	public LoadableParticleVO(String tex)
	{
		texture = tex;
	}

	@Override
	public String getTexture()
	{
		return texture;
	}
	
	@Override
	public void setGameTexture(AbstractTexture gameTexture)
	{
		this.gameTexture = gameTexture;
	}
}
