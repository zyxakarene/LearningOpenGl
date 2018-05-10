package zyx.opengl.particles;

import zyx.opengl.models.implementations.ParticleModel;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;
import zyx.utils.interfaces.IUpdateable;

public class ParticleSystem implements IUpdateable, IDrawable, IDisposeable
{
	
	private ParticleModel model;

	public ParticleSystem()
	{
		model = new ParticleModel();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
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
	}
}
