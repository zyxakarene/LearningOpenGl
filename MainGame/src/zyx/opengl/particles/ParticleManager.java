package zyx.opengl.particles;

import java.util.ArrayList;
import zyx.opengl.GLUtils;
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
		GLUtils.disableCulling();
		for (ParticleSystem system : systems)
		{
			system.draw();
		}
		GLUtils.enableCulling();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (ParticleSystem system : systems)
		{
			system.update(timestamp, elapsedTime);
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
	}
}
