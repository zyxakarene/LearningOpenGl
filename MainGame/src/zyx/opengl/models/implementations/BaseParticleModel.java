package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.implementations.renderers.wrappers.AbstractParticleModelWrapper;

public abstract class BaseParticleModel extends AbstractInstancedModel<ParticleModelMaterial> implements IParticleModel
{

	public BaseParticleModel()
	{
	}
	
	@Override
	public abstract AbstractParticleModelWrapper createWrapper();
}
