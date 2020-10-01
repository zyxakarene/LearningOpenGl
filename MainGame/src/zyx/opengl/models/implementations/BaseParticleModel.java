package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.implementations.renderers.AbstractParticleRenderer;

public abstract class BaseParticleModel extends AbstractInstancedModel<ParticleModelMaterial> implements IParticleModel
{

	public BaseParticleModel(ParticleModelMaterial material)
	{
		super(material);
		setup();
	}

	@Override
	public abstract AbstractParticleRenderer createRenderer();
}
