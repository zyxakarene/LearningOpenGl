package zyx.opengl.models.implementations.renderers.wrappers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.implementations.ParticleModel;
import zyx.opengl.models.implementations.renderers.ParticleRenderer;

public class ParticleModelWrapper extends AbstractParticleModelWrapper<ParticleModelMaterial, ParticleModel, ParticleRenderer>
{

	public ParticleModelWrapper(ParticleRenderer[] renderers, ParticleModel model)
	{
		super(renderers, model);
	}
}
