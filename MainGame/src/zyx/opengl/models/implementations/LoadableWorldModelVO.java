package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.textures.AbstractTexture;

public class LoadableWorldModelVO implements ILoadableVO
{

	float vertexData[];
	int elementData[];
	Skeleton skeleton;
	String texture;
	PhysBox physBox;
	Vector3f radiusCenter;
	float radius;
	
	AbstractTexture gameTexture;

	public LoadableWorldModelVO(float[] vertexData, int[] elementData, Skeleton skeleton, PhysBox physBox, String texture, Vector3f radiusCenter, float radius)
	{
		this.vertexData = vertexData;
		this.elementData = elementData;
		this.skeleton = skeleton;
		this.texture = texture;
		this.physBox = physBox;
		this.radiusCenter = radiusCenter;
		this.radius = radius;
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
