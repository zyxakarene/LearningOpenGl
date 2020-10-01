package zyx.opengl.particles;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.ParticleResource;
import zyx.opengl.camera.Camera;
import zyx.opengl.models.implementations.renderers.AbstractParticleRenderer;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.FloatMath;

public class ParticleSystem extends WorldObject implements IResourceReady<ParticleResource>
{
	private static final Vector3f HELPER_VECTOR = new Vector3f();

	public float particleTime;
	public float parentScale;

	protected boolean loaded;
	protected String resource;
	protected AbstractParticleRenderer renderer;
	
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
			renderer.update(timestamp, elapsedTime);
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
		renderer = resource.getContent();
		if (renderer.isWorldParticle() == false)
		{
			particleTime += (1000 * FloatMath.random());
		}
		
		renderer.setup(this);
		loaded = true;
	}

	@Override
	public boolean inView()
	{
		getPosition(false, HELPER_VECTOR);
		
		float radius = renderer.getRadius() * parentScale;
		
		boolean visible = Camera.getInstance().isInView(HELPER_VECTOR, radius);
		return visible;
	}

	void drawParticle()
	{
		if (loaded && inView())
		{
			SharedShaderObjects.SHARED_WORLD_MODEL_TRANSFORM.load(worldMatrix());
			
			renderer.draw();
		}
	}

	@Override
	protected void onDispose()
	{
		ParticleManager.getInstance().remove(this);
		
		if (renderer != null)
		{
			if (renderer.isWorldParticle())
			{
				particleResource.removeParticleInstance(renderer);
			}
			
			renderer.dispose();
			renderer = null;
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
