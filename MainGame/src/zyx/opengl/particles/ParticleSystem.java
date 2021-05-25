package zyx.opengl.particles;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.ParticleResource;
import zyx.opengl.camera.Camera;
import zyx.opengl.models.implementations.renderers.AbstractParticleRenderer;
import zyx.opengl.models.implementations.renderers.wrappers.AbstractParticleModelWrapper;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.FloatMath;

public class ParticleSystem extends WorldObject implements IResourceReady<ParticleResource>
{
	private static final Vector3f HELPER_VECTOR = new Vector3f();

	public float particleTime;
	public float parentScale;

	protected boolean loaded;
	protected String resource;
	protected AbstractParticleModelWrapper wrapper;
	
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
			wrapper.update(timestamp, elapsedTime);
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
		wrapper = resource.getContent();
		if (wrapper.isWorldParticle() == false)
		{
			particleTime += (1000 * FloatMath.random());
		}
		
		wrapper.setup(this);
		loaded = true;
	}

	@Override
	public boolean inView()
	{
		getPosition(false, HELPER_VECTOR);
		
		float radius = wrapper.getRadius() * parentScale;
		
		boolean visible = Camera.getInstance().isInView(HELPER_VECTOR, radius);
		return visible;
	}

	void drawParticle()
	{
		if (loaded && inView())
		{
			SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(worldMatrix());
			
			wrapper.draw(0);
		}
	}

	@Override
	protected void onDispose()
	{
		ParticleManager.getInstance().remove(this);
		
		if (wrapper != null)
		{
			if (wrapper.isWorldParticle())
			{
				particleResource.removeParticleInstance(wrapper);
			}
			
			wrapper.dispose();
			wrapper = null;
		}
		
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

	@Override
	public String getDebugIcon()
	{
		return "particle.png";
	}
}
