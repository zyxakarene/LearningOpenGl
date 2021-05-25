package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;

public abstract class AbstractParticleRenderer<TModel extends AbstractInstancedModel<ParticleModelMaterial>> extends MeshRenderer<ParticleModelMaterial, TModel>
{
	public AbstractParticleRenderer(TModel model, int meshIndex, ParticleModelMaterial defaultMaterial)
	{
		super(model, meshIndex, defaultMaterial);
	}
}
