package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.textures.AbstractTexture;

public class LoadableWorldModelVO
{

	float vertexData[];
	int elementData[];
	Skeleton skeleton;
	String diffuseTextureId;
	String normalTextureId;
	String specularTextureId;
	PhysBox physBox;
	Vector3f radiusCenter;
	float radius;
	
	AbstractTexture gameTexture;
	AbstractTexture normalTexture;
	AbstractTexture specularTexture;

	public LoadableWorldModelVO(float[] vertexData, int[] elementData, Skeleton skeleton, PhysBox physBox, String diffuse, String normal, String specular,
			Vector3f radiusCenter, float radius)
	{
		this.vertexData = vertexData;
		this.elementData = elementData;
		this.skeleton = skeleton;
		this.diffuseTextureId = diffuse;
		this.normalTextureId = normal;
		this.specularTextureId = specular;
		this.physBox = physBox;
		this.radiusCenter = radiusCenter;
		this.radius = radius;
	}

	public String getDiffuseTextureId()
	{
		return diffuseTextureId;
	}

	public String getNormalTextureId()
	{
		return normalTextureId;
	}

	public String getSpecularTextureId()
	{
		return specularTextureId;
	}
	
	public void setDiffuseTexture(AbstractTexture gameTexture)
	{
		this.gameTexture = gameTexture;
	}
	
	public void setNormalTexture(AbstractTexture gameTexture)
	{
		normalTexture = gameTexture;
	}

	public void setSpecularTexture(AbstractTexture gameTexture)
	{
		specularTexture = gameTexture;
	}
	
	public void dispose()
	{
		if(skeleton != null)
		{
			skeleton.dispose();
			skeleton = null;
		}
		
		vertexData = null;
		elementData = null;
		physBox = null;
	}
}
