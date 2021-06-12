package zyx.opengl.models.implementations.renderers.wrappers;

import zyx.engine.components.world.WorldObject;
import zyx.opengl.materials.Material;
import zyx.opengl.models.AbstractMultiModel;
import zyx.opengl.models.implementations.IParticleModel;
import zyx.opengl.models.implementations.LoadableParticleVO;
import zyx.opengl.models.implementations.renderers.MeshRenderer;
import zyx.opengl.particles.ParticleSystem;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.shaders.implementations.WorldParticleShader;

public abstract class AbstractParticleModelWrapper
		<
			TMaterial extends Material,
			TModel extends AbstractMultiModel<TMaterial> & IParticleModel, 
			TRenderer extends MeshRenderer<TMaterial, TModel>
		> 
		extends MeshWrapper<TMaterial, TModel, TRenderer> implements IParticleModel
{

	private ParticleSystem system;

	public AbstractParticleModelWrapper(TRenderer[] renderers, TModel model)
	{
		super(renderers, model);
	}
	
	@Override
	protected void onPreDraw()
	{
		super.onPreDraw();
		
		if (system != null)
		{
			ParticleShader.parentScale = system.parentScale;
			WorldParticleShader.parentScale = system.parentScale;
			SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(system.worldMatrix());
		}
	}
	
	public void setup(ParticleSystem system)
	{
		this.system = system;
		
		model.setParent(system);
		
		super.setup(system);
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
	public void setParent(WorldObject parent)
	{
		model.setParent(parent);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		model.update(timestamp, elapsedTime);
	}
}
