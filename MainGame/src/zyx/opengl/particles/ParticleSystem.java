package zyx.opengl.particles;

import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.ParticleResource;
import zyx.engine.resources.impl.Resource;
import zyx.opengl.models.implementations.IParticleModel;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.FloatMath;

public class ParticleSystem extends WorldObject implements IResourceReady<ParticleResource>
{
	private static final Vector3f HELPER_VECTOR = new Vector3f();

	float particleTime;
	float parentScale;

	protected boolean loaded;
	protected String path;
	protected IParticleModel model;
	
	private Resource particleResource;

	public ParticleSystem()
	{
		super(Shader.WORLD);

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
		this.path = resource;
		
		particleResource = ResourceManager.getInstance().getResource(resource);
		particleResource.registerAndLoad(this);
	}

	@Override
	public void onResourceReady(ParticleResource resource)
	{
		IParticleModel data = resource.getContent();
		
		if (data.isWorldParticle())
		{
			model = data.cloneParticle();
		}
		else
		{
			model = data;
			particleTime += (1000 * FloatMath.random());
		}
		
		model.setParent(this);
		loaded = true;
	}

	@Override
	protected void onDraw()
	{
	}

	void drawParticle()
	{
		if (loaded)
		{
			SharedShaderObjects.SHARED_MODEL_TRANSFORM.load(worldMatrix());
			
			model.draw();
		}
	}

	@Override
	protected void onDispose()
	{
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
		return String.format("%s{%s}", getClass().getSimpleName(), path);
	}
}
