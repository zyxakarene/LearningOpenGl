package zyx.game.controls.sound;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.game.components.GameObject;
import zyx.opengl.shaders.SharedShaderObjects;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class Sound implements IDisposeable, IUpdateable
{

	private static final Vector4f SHARED_VECTOR_4F = new Vector4f();
	private static final Vector3f SHARED_VECTOR_3F = new Vector3f();

	final int soundId;

	private AudioWrapper audio;
	private GameObject emitter;

	private float prevPosition;
	private boolean stopped;
	private boolean loop;

	Sound(int soundId)
	{
		this.soundId = soundId;
	}

	void set(float volume, boolean loop, AudioWrapper audio, GameObject emitter)
	{
		this.loop = loop;
		this.audio = audio;
		this.emitter = emitter;

		prevPosition = 0;
		stopped = false;

		audio.playAsSoundEffect(volume, SHARED_VECTOR_4F);
	}

	@Override
	public void dispose()
	{
		audio.stop();

		audio = null;
		emitter = null;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (stopped)
		{
			return;
		}
		
		if (emitter.disposed)
		{
			stop();
			return;
		}

		emitter.getPosition(false, SHARED_VECTOR_3F);
		SHARED_VECTOR_4F.x = SHARED_VECTOR_3F.x;
		SHARED_VECTOR_4F.y = SHARED_VECTOR_3F.y;
		SHARED_VECTOR_4F.z = SHARED_VECTOR_3F.z;
		SHARED_VECTOR_4F.w = 1;

		Matrix4f.transform(SharedShaderObjects.SHARED_VIEW_TRANSFORM, SHARED_VECTOR_4F, SHARED_VECTOR_4F);
		float time = audio.getPosition();
		
		if (!loop && time <= 0 && time < prevPosition)
		{
			stop();
		}
		else
		{
			audio.updatePosition(SHARED_VECTOR_4F);
			prevPosition = time;
		}
	}

	private void stop()
	{
		audio.stop();
		SoundManager.getInstance().onSoundDone(this);
	}
}
