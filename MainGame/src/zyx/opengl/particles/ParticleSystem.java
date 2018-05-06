package zyx.opengl.particles;

import zyx.opengl.models.implementations.ParticleModel;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;
import zyx.utils.interfaces.IUpdateable;

public class ParticleSystem implements IUpdateable, IDrawable, IDisposeable
{
	
	private ParticleModel model;
	private ParticleEntity entity;

	public ParticleSystem()
	{
		model = new ParticleModel();
		entity = new ParticleEntity();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		entity.update(timestamp, elapsedTime);
	}

	@Override
	public void draw()
	{
		model.draw();
	}

	@Override
	public void dispose()
	{
		model.dispose();

		model = null;
		entity = null;
	}
	
}
