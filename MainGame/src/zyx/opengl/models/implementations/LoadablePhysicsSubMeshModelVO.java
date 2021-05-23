package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.DepthMaterial;
import zyx.opengl.materials.impl.PhysicsModelMaterial;

public class LoadablePhysicsSubMeshModelVO extends AbstractLoadableSubMeshModelVO<PhysicsModelMaterial>
{

	@Override
	protected void createMaterials()
	{
		material = new PhysicsModelMaterial(worldShader);
		shadowMaterial = new DepthMaterial(shadowShader);
	}

}
