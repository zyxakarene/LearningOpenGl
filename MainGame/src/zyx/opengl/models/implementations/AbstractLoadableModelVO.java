package zyx.opengl.models.implementations;

import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;

public abstract class AbstractLoadableModelVO<TMaterial extends WorldModelMaterial>
{
	public final int subMeshCount;
	AbstractLoadableSubMeshModelVO<TMaterial>[] subMeshes;
	
	String skeletonId;
	Skeleton skeleton;

	Vector3f radiusCenter;
	float radius;

	public AbstractLoadableModelVO(int subMeshCount)
	{
		this.subMeshCount = subMeshCount;
		
		subMeshes = new AbstractLoadableSubMeshModelVO[subMeshCount];
		for (int i = 0; i < subMeshCount; i++)
		{
			subMeshes[i] = createSubMeshVO();
		}
	}
	
	protected abstract AbstractLoadableSubMeshModelVO<TMaterial> createSubMeshVO();
	
	public ISubMeshVO getSubMeshVO(int index)
	{
		return subMeshes[index];
	}
	
	public void asWorldModel()
	{
		for (AbstractLoadableSubMeshModelVO<TMaterial> mesh : subMeshes)
		{
			mesh.asWorldModel();
		}
	}
	
	public void asMeshBatch()
	{
		for (AbstractLoadableSubMeshModelVO<TMaterial> mesh : subMeshes)
		{
			mesh.asMeshBatch();
		}
	}
	
	public void asSkybox()
	{
		for (AbstractLoadableSubMeshModelVO<TMaterial> mesh : subMeshes)
		{
			mesh.asSkybox();
		}
	}
	
	public void setSkeletonId(String skeletonId)
	{
		this.skeletonId = skeletonId;
	}

	public String getSkeletonId()
	{
		return skeletonId;
	}
	

	public void setSkeleton(Skeleton skeleton)
	{
		this.skeleton = skeleton;
	}
	
	public void setRadius(Vector3f radiusCenter, float radius)
	{
		this.radiusCenter = radiusCenter;
		this.radius = radius;
	}
	
	public void dispose()
	{
		if (subMeshes != null)
		{
			for (AbstractLoadableSubMeshModelVO<TMaterial> mesh : subMeshes)
			{
				mesh.dispose();
			}
			subMeshes = null;
		}
		
		radiusCenter = null;
	}

	public void clean()
	{
		for (AbstractLoadableSubMeshModelVO<TMaterial> mesh : subMeshes)
		{
			mesh.vertexData = null;
			mesh.elementData = null;
		}
	}
}
