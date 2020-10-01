package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.PhysicsModelMaterial;

public class LoadablePhysicsModelVO extends AbstractLoadableModelVO<PhysicsModelMaterial>
{
	
	@Override
	protected void createMaterials()
	{
		material = new PhysicsModelMaterial(worldShader);
		shadowMaterial = new PhysicsModelMaterial(shadowShader);
	}
}
