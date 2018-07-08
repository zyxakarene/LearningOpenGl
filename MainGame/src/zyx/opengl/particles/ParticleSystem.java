package zyx.opengl.particles;

import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;

public class ParticleSystem extends AbstractParticleSystem
{

	public ParticleSystem()
	{
		super(Shader.PARTICLE);
	}

	@Override
	protected void onDraw()
	{
	}

	@Override
	void drawParticle()
	{
		if (loaded)
		{
			SharedShaderObjects.SHARED_MODEL_TRANSFORM.load(worldMatrix());

			shader.bind();
			shader.upload();
			model.draw();
		}
	}

	@Override
	protected void onDispose()
	{
		model = null;
	}

	@Override
	public String toString()
	{
		return String.format("%s{%s}", getClass().getSimpleName(), path);
	}

}
