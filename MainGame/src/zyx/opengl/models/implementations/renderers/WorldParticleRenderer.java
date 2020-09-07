package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.WorldParticleModel;

public class WorldParticleRenderer extends AbstractParticleRenderer<WorldParticleModel>
{
	public WorldParticleRenderer(WorldParticleModel model, ParticleModelMaterial defaultMaterial)
	{
		super(model, defaultMaterial);
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
	protected void onDispose()
	{
		model.dispose();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		model.update(timestamp, elapsedTime);
	}
}
