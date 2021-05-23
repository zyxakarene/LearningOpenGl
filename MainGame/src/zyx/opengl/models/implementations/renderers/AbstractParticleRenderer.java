package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.ParticleModelMaterial;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.implementations.IParticleModel;
import zyx.opengl.particles.ParticleSystem;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.shaders.implementations.WorldParticleShader;

public abstract class AbstractParticleRenderer<TModel extends AbstractInstancedModel<ParticleModelMaterial>> extends MeshRenderer<ParticleModelMaterial, TModel> implements IParticleModel
{

	private ParticleSystem system;
	
	public AbstractParticleRenderer(TModel model, ParticleModelMaterial[] defaultMaterials)
	{
		super(model, defaultMaterials);
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
		setParent(system);
		
		super.setup(system);
	}
}
