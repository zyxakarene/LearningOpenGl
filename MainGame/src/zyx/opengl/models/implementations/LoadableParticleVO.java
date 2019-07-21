package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.textures.AbstractTexture;

public class LoadableParticleVO
{

	public int instanceCount = 10;
	
	public Vector3f gravity = new Vector3f(0, 0, 0);
	
	public Vector2f areaX = new Vector2f(0, 0);
	public Vector2f areaY = new Vector2f(0, 0);
	public Vector2f areaZ = new Vector2f(0, 0);
	
	public Vector3f speed = new Vector3f(0, 0, 0);
	public Vector3f speedVariance = new Vector3f(0, 0, 0);
	
	public Vector4f startColor = new Vector4f(1, 1, 1, 1);
	public Vector4f endColor = new Vector4f(1, 1, 1, 1);
	
	public float startScale = 1;
	public float endScale = 1;
	public float scaleVariance = 0;
	
	public float lifespan = 1000;
	public float lifespanVariance = 0;
	
	public float rotation = 1000;
	public float rotationVariance = 0;
	
	public float radius = 0;
	
	public boolean worldParticle = false;
	
	String texture = "particle.png";
	AbstractTexture gameTexture;

	public LoadableParticleVO()
	{
	}

	public String getDiffuseTextureId()
	{
		return texture;
	}
	
	public void setDiffuseTexture(AbstractTexture gameTexture)
	{
		this.gameTexture = gameTexture;
	}
}
