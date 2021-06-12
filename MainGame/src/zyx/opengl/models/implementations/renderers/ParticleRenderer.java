package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.implementations.ParticleModel;

public class ParticleRenderer extends AbstractParticleRenderer<ParticleModel>
{	
	public ParticleRenderer(ParticleModel model, int meshIndex, ParticleModelMaterial defaultMaterial)
	{
		super(model, meshIndex, defaultMaterial);
	}
}
