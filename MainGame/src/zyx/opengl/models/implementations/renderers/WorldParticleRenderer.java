package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.implementations.WorldParticleModel;

public class WorldParticleRenderer extends AbstractParticleRenderer<WorldParticleModel>
{
	public WorldParticleRenderer(WorldParticleModel model, int meshIndex, ParticleModelMaterial defaultMaterial)
	{
		super(model, meshIndex, defaultMaterial);
	}
}
