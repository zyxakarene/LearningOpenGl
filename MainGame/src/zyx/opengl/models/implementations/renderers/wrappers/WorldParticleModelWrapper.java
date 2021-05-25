package zyx.opengl.models.implementations.renderers.wrappers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.implementations.WorldParticleModel;
import zyx.opengl.models.implementations.renderers.WorldParticleRenderer;

public class WorldParticleModelWrapper extends AbstractParticleModelWrapper<ParticleModelMaterial, WorldParticleModel, WorldParticleRenderer>
{

	public WorldParticleModelWrapper(WorldParticleRenderer[] renderers, WorldParticleModel model)
	{
		super(renderers, model);
	}
}
