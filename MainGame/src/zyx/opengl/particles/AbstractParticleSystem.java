package zyx.opengl.particles;

import zyx.engine.components.world.WorldObject;
import zyx.game.controls.loading.particle.ParticleLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.opengl.models.AbstractInstancedModel;
import zyx.opengl.shaders.implementations.Shader;

public abstract class AbstractParticleSystem extends WorldObject implements IResourceLoaded<AbstractInstancedModel>
{
	float particleTime;
	
	protected boolean loaded;
	protected AbstractInstancedModel model;
	protected String path;

	public AbstractParticleSystem(Shader shader)
	{
		super(shader);
		
		loaded = false;
		ParticleManager.getInstance().add(this);
		setZ(-3);
	}

	final void update(long timestamp, int elapsedTime)
	{
		particleTime += (elapsedTime * 1f);
		
		onUpdate(timestamp, elapsedTime);
	}
	
	public void load(String path)
	{
		this.path = path;
		ParticleLoader.getInstance().load(path, this);
	}
	
	@Override
	public void resourceLoaded(AbstractInstancedModel data)
	{
		model = data;
		loaded = true;
	}
	
	@Override
	protected void onDraw()
	{
	}
	
	void drawParticle()
	{
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

	protected void onUpdate(long timestamp, int elapsedTime)
	{
	}
}
