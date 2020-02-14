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
	String skeletonId;
	String diffuseTextureId;
	String normalTextureId;
	String specularTextureId;
	PhysBox physBox;
	Vector3f radiusCenter;
	float radius;
	
	AbstractTexture gameTexture;
	AbstractTexture normalTexture;
	AbstractTexture specularTexture;

	public LoadableWorldModelVO(float[] vertexData, int[] elementData, PhysBox physBox, String diffuse, String normal, String specular,
			Vector3f radiusCenter, float radius, String skeletonId)
	{
		this.vertexData = vertexData;
		this.elementData = elementData;
		this.diffuseTextureId = diffuse;
		this.normalTextureId = normal;
		this.specularTextureId = specular;
		this.physBox = physBox;
		this.radiusCenter = radiusCenter;
		this.radius = radius;
		this.skeletonId = skeletonId;
	}

	public void setSkeleton(Skeleton skeleton)
	{
		this.skeleton = skeleton;
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

	public String getSkeletonId()
	{
		return skeletonId;
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
		
		if (physBox != null)
		{
			physBox.dispose();
		}
		
		vertexData = null;
		elementData = null;
		physBox = null;
	}

	public void clean()
	{
		vertexData = null;
		elementData = null;
	}
}
