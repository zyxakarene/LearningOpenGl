package zyx.opengl.particles;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.ParticleModel;
import zyx.opengl.shaders.implementations.ParticleShader;
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
