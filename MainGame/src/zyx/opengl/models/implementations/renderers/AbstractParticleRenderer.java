package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.implementations.IParticleModel;

public abstract class AbstractParticleRenderer<TModel extends AbstractInstancedModel<ParticleModelMaterial>> extends MeshRenderer<ParticleModelMaterial, TModel> implements IParticleModel
{
	public AbstractParticleRenderer(TModel model, ParticleModelMaterial defaultMaterial)
	{
		super(model, defaultMaterial);
	}
}
