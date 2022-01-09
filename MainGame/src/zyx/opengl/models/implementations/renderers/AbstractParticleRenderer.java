package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.particles.ParticleSystem;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.shaders.implementations.WorldParticleShader;

public abstract class AbstractParticleRenderer<TModel extends AbstractInstancedModel<ParticleModelMaterial>> extends MeshRenderer<ParticleModelMaterial, TModel>
{

	private ParticleSystem system;
	
	public AbstractParticleRenderer(TModel model, int meshIndex, ParticleModelMaterial defaultMaterial)
	{
		super(model, meshIndex, defaultMaterial);
	}

	public void setup(ParticleSystem system)
	{
		this.system = system;
		
		super.setParent(system);
	}

	@Override
	protected void onPreDraw()
	{
		super.onPreDraw();
		
		if (system != null)
		{
			ParticleShader.parentScale = system.parentScale;
			SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(system.worldMatrix());
		}
	}
}
