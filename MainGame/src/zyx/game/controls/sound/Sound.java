package zyx.game.controls.sound;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.resources.impl.SoundResource;
import zyx.engine.sound.IAudio;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class Sound implements IDisposeable, IUpdateable, IResourceReady<SoundResource>
{

	private static final Vector4f SHARED_VECTOR_4F = new Vector4f();
	private static final Vector3f SHARED_VECTOR_3F = new Vector3f();

	final int soundId;

	private WorldObject emitter;
	private Vector3f position;

	private float prevPosition;
	private boolean stopped;
	private boolean loop;
	private Resource soundResource;
	private IAudio audio;
	private boolean loaded;
	private float volume;

	Sound(int soundId)
	{
		this.soundId = soundId;
		this.position = new Vector3f();
	}

	private void set(float volume, boolean loop, String resource)
	{
		this.loop = loop;
		this.volume = volume;

		prevPosition = 0;
		stopped = false;
		loaded = false;

		soundResource = ResourceManager.getInstance().getResource(resource);
		soundResource.registerAndLoad(this);
	}
	
	void set(float volume, boolean loop, String resource, WorldObject emitter)
	{
		set(volume, loop, resource);
		this.emitter = emitter;
	}

	void set(float volume, boolean loop, String resource, Vector3f position)
	{
		set(volume, loop, resource);
		this.position.set(position);
	}

	@Override
	public void onResourceReady(SoundResource resource)
	{
		audio = resource.getContent();
		loaded = true;

		getEmitterPosition();
		audio.playAt(SHARED_VECTOR_4F.x, SHARED_VECTOR_4F.y, SHARED_VECTOR_4F.z, volume, loop);
	}

	@Override
	public void dispose()
	{
		if (soundResource != null)
		{
			soundResource.unregister(this);
			soundResource = null;
		}

		audio = null;
		emitter = null;
		position.set(0, 0, 0);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (stopped || !loaded)
		{
			return;
		}

		if (emitter != null && emitter.disposed)
		{
			stop();
			return;
		}

		getEmitterPosition();

		float time = audio.getPosition();

		if (!loop && time <= 0 && time < prevPosition)
		{
			stop();
		}
		else
		{
			audio.setListenerPosition(SHARED_VECTOR_4F.x, SHARED_VECTOR_4F.y, SHARED_VECTOR_4F.z);
			prevPosition = time;
		}
	}

	private void getEmitterPosition()
	{
		if (emitter != null)
		{
			emitter.getPosition(false, SHARED_VECTOR_3F);
		}
		else
		{
			SHARED_VECTOR_3F.set(position);
		}
		
		SHARED_VECTOR_4F.x = SHARED_VECTOR_3F.x;
		SHARED_VECTOR_4F.y = SHARED_VECTOR_3F.y;
		SHARED_VECTOR_4F.z = SHARED_VECTOR_3F.z;
		SHARED_VECTOR_4F.w = 1;

		Matrix4f.transform(SharedShaderObjects.SHARED_WORLD_VIEW_TRANSFORM, SHARED_VECTOR_4F, SHARED_VECTOR_4F);
	}

	private void stop()
	{
		audio.stop();
		SoundManager.getInstance().onSoundDone(this);
	}
}
