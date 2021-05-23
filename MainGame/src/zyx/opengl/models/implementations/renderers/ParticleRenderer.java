package zyx.opengl.models.implementations.renderers;

import zyx.engine.components.world.WorldObject;
import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.ParticleModel;

public class ParticleRenderer extends AbstractParticleRenderer<ParticleModel>
{	
	public ParticleRenderer(ParticleModel model, ParticleModelMaterial[] defaultMaterials)
	{
		super(model, defaultMaterials);
	}
	
	@Override
	public boolean isWorldParticle()
	{
		return model.isWorldParticle();
	}

	@Override
	public float getRadius()
	{
		return model.getRadius();
	}

	@Override
	public void refresh(LoadableParticleVO loadedVo)
	{
		model.refresh(loadedVo);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		model.update(timestamp, elapsedTime);
	}

	@Override
	public void setParent(WorldObject parent)
	{
		model.setParent(parent);
	}
}
