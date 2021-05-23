package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.WorldModelMaterial;

public class LoadableWorldModelVO extends AbstractLoadableModelVO<WorldModelMaterial>
{

	public LoadableWorldModelVO(int subMeshCount)
	{
		super(subMeshCount);
	}
	
	@Override
	protected AbstractLoadableSubMeshModelVO<WorldModelMaterial> createSubMeshVO()
	{
		return new LoadableWorldSubMeshModelVO();
	}
}
