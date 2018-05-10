package zyx.opengl.particles;

import java.util.ArrayList;
import zyx.opengl.GLUtils;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IDrawable;
import zyx.utils.interfaces.IUpdateable;

public class ParticleManager implements IDrawable, IUpdateable, IDisposeable
{
	private static ParticleManager instance = new ParticleManager();

	private ArrayList<ParticleSystem> systems;
	
	public static ParticleManager getInstance()
	{
		return instance;
	}

	private ParticleManager()
	{
		systems = new ArrayList<>();
	}

	public void add(ParticleSystem system)
	{
		systems.add(system);
	}
	
	@Override
	public void draw()
	{
		GLUtils.setBlendAdditive();
		GLUtils.disableCulling();
		GLUtils.disableDepthWrite();
		for (ParticleSystem system : systems)
		{
			ParticleShader.elapsedTime = system.elapsedTime;
			system.drawParticle();
		}
		GLUtils.enableDepthWrite();
		GLUtils.enableCulling();
		GLUtils.setBlendAlpha();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (ParticleSystem system : systems)
		{
			system.elapsedTime += elapsedTime;
		}
	}

	@Override
	public void dispose()
	{
		for (ParticleSystem system : systems)
		{
			system.dispose();
		}
		
		systems.clear();
		
		ParticleShader.elapsedTime = 0;
	}
}
