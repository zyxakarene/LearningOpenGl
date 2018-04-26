package zyx.game.scene.particle;

import zyx.engine.scene.Scene;
import zyx.game.components.GameObject;
import zyx.opengl.models.implementations.ParticleModel;
import zyx.opengl.particles.ParticleManager;
import zyx.opengl.particles.ParticleSystem;

public class ParticleScene extends Scene
{
				
	public ParticleScene()
	{
	}

	@Override
	protected void onInitialize()
	{
		ParticleManager.getInstance().add(new ParticleSystem());
	}

	@Override
	protected void onUpdate(long timestamp, int elapsedTime)
	{
		ParticleManager.getInstance().update(timestamp, elapsedTime);
	}

	@Override
	protected void onDraw()
	{
		ParticleManager.getInstance().draw();
	}

	@Override
	protected void onDispose()
	{
		ParticleManager.getInstance().dispose();
	}
	
}
