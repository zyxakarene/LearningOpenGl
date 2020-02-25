package zyx.opengl.particles;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.ParticleResource;
import zyx.opengl.camera.Camera;
import zyx.opengl.models.implementations.IParticleModel;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.FloatMath;

public class ParticleSystem extends WorldObject implements IResourceReady<ParticleResource>
{
	private static final Vector3f HELPER_VECTOR = new Vector3f();

	float particleTime;
	float parentScale;

	protected boolean loaded;
	protected String resource;
	protected IParticleModel model;
	
	private ParticleResource particleResource;

	public ParticleSystem()
	{
		loaded = false;
		ParticleManager.getInstance().add(this);
	}

	final void update(long timestamp, int elapsedTime)
	{
		particleTime += (elapsedTime * 1f);
		
		if (loaded)
		{
			getScale(false, HELPER_VECTOR);
			
			parentScale = HELPER_VECTOR.y;
			model.update(timestamp, elapsedTime);
		}
	}

	public void load(String resource)
	{
		this.resource = resource;
		
		particleResource = ResourceManager.getInstance().<ParticleResource>getResourceAs(resource);
		particleResource.registerAndLoad(this);
	}

	public String getResource()
	{
		return resource;
	}
	
	@Override
	public void onResourceReady(ParticleResource resource)
	{
		model = resource.getContent();
		if (model.isWorldParticle() == false)
		{
			particleTime += (1000 * FloatMath.random());
		}
		
		model.setParent(this);
		loaded = true;
	}

	@Override
	protected void onDraw()
	{
	}

	@Override
	public boolean inView()
	{
		getPosition(false, HELPER_VECTOR);
		
		float radius = model.getRadius() * parentScale;
		
		boolean visible = Camera.getInstance().isInView(HELPER_VECTOR, radius);
		return visible;
	}

	void drawParticle()
	{
		if (loaded && inView())
		{
			SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(worldMatrix());
			
			model.draw();
		}
	}

	@Override
	protected void onDispose()
	{
		ParticleManager.getInstance().remove(this);
		
		if (model != null && model.isWorldParticle())
		{
			particleResource.removeParticleInstance(model);
			model.dispose();
		}
		
		model = null;
		
		if (particleResource != null)
		{
			particleResource.unregister(this);
			particleResource = null;
		}
	}

	@Override
	public String toString()
	{
		return String.format("%s{%s}", getClass().getSimpleName(), resource);
	}
}
