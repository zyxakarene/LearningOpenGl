package zyx.opengl.particles;

import java.util.ArrayList;
import zyx.opengl.shaders.implementations.ParticleShader;
import zyx.opengl.shaders.implementations.WorldParticleShader;
import zyx.utils.interfaces.IUpdateable;

public class ParticleManager implements IUpdateable
{
	private static final ParticleManager INSTANCE = new ParticleManager();

	private ArrayList<ParticleSystem> systems;
	
	public static ParticleManager getInstance()
	{
		return INSTANCE;
	}

	private ParticleManager()
	{
		systems = new ArrayList<>();
	}

	void add(ParticleSystem system)
	{
		systems.add(system);
	}
	
	void remove(ParticleSystem system)
	{
		systems.remove(system);
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (ParticleSystem system : systems)
		{
			system.update(timestamp, elapsedTime);
		}
	}

	public void clear()
	{
		systems.clear();
		
		ParticleShader.elapsedTime = 0;
		WorldParticleShader.elapsedTime = 0;
	}
}
