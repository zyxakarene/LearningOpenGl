package zyx.opengl.particles;

import java.util.ArrayList;
import zyx.opengl.GLUtils;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.shaders.implementations.WorldParticleShader;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;
import zyx.utils.interfaces.IUpdateable;

public class ParticleManager implements IDrawable, IUpdateable, IDisposeable
{
	private static ParticleManager instance = new ParticleManager();

	private ArrayList<AbstractParticleSystem> systems;
	
	public static ParticleManager getInstance()
	{
		return instance;
	}

	private ParticleManager()
	{
		systems = new ArrayList<>();
	}

	public void add(AbstractParticleSystem system)
	{
		systems.add(system);
	}
	
	@Override
	public void draw()
	{
		GLUtils.setBlendAdditive();
		GLUtils.disableCulling();
		GLUtils.disableDepthWrite();
		for (AbstractParticleSystem system : systems)
		{
			ParticleShader.elapsedTime = system.particleTime;
			WorldParticleShader.elapsedTime = system.particleTime;
			system.drawParticle();
		}
		GLUtils.enableDepthWrite();
		GLUtils.enableCulling();
		GLUtils.setBlendAlpha();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (AbstractParticleSystem system : systems)
		{
			system.update(timestamp, elapsedTime);
		}
	}

	@Override
	public void dispose()
	{
		for (AbstractParticleSystem system : systems)
		{
			system.dispose();
		}
		
		systems.clear();
		
		ParticleShader.elapsedTime = 0;
		WorldParticleShader.elapsedTime = 0;
	}
}
