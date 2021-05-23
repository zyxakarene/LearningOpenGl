package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.PhysicsModelMaterial;

public class LoadablePhysicsModelVO extends AbstractLoadableModelVO<PhysicsModelMaterial>
{

	public LoadablePhysicsModelVO(int subMeshCount)
	{
		super(subMeshCount);
	}

	@Override
	protected AbstractLoadableSubMeshModelVO<PhysicsModelMaterial> createSubMeshVO()
	{
		return new LoadablePhysicsSubMeshModelVO();
	}
}
