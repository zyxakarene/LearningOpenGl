package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.DepthMaterial;
import zyx.opengl.materials.impl.WorldModelMaterial;

public class LoadableWorldSubMeshModelVO extends AbstractLoadableSubMeshModelVO<WorldModelMaterial>
{

	@Override
	protected void createMaterials()
	{
		material = new WorldModelMaterial(worldShader);
		shadowMaterial = new DepthMaterial(shadowShader);
	}

}
