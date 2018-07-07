package zyx.opengl.particles;

import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.models.implementations.WorldParticleModel;
import zyx.opengl.shaders.implementations.Shader;

public class WorldParticleSystem extends AbstractParticleSystem
{

	private WorldParticleModel worldParticle;

	public WorldParticleSystem()
	{
		super(Shader.WORLD_PARTICLE);
	}

	@Override
	protected void onDraw()
	{
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		if (worldParticle != null)
		{
			worldParticle.update(timestamp, elapsedTime);
		}
	}

	@Override
	public void resourceLoaded(AbstractInstancedModel data)
	{
		super.resourceLoaded(data);
		
		this.worldParticle = (WorldParticleModel) data;
		this.worldParticle.setParent(this);
	}
	
	@Override
	void drawParticle()
	{
		if (loaded)
		{
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
