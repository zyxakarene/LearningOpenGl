package zyx.game.controls.sound;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.interfaces.IUpdateable;

public class Sound implements IDisposeable, IUpdateable
{

	private static final Vector4f SHARED_VECTOR_4F = new Vector4f();
	private static final Vector3f SHARED_VECTOR_3F = new Vector3f();

	final int soundId;

	private float volume;
	private AudioWrapper audio;
	private IPositionable position;

	private float prevPosition;
	private boolean stopped;
	private boolean loop;
	private boolean started;

	Sound(int soundId)
	{
		this.soundId = soundId;
	}

	void set(float volume, boolean loop, AudioWrapper audio, IPositionable position)
	{
		this.volume = volume;
		this.loop = loop;
		this.audio = audio;
		this.position = position;

		prevPosition = 0;
		stopped = false;
		started = false;

		audio.playAsSoundEffect(volume, SHARED_VECTOR_4F);
		audio.setPosition(0);
	}

	@Override
	public void dispose()
	{
		audio.stop();

		audio = null;
		position = null;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (stopped)
		{
			return;
		}

		position.getWorldPosition(SHARED_VECTOR_3F);
		SHARED_VECTOR_4F.x = SHARED_VECTOR_3F.x;
		SHARED_VECTOR_4F.y = SHARED_VECTOR_3F.y;
		SHARED_VECTOR_4F.z = SHARED_VECTOR_3F.z;
		SHARED_VECTOR_4F.w = 1;

		Matrix4f.transform(WorldShader.MATRIX_VIEW, SHARED_VECTOR_4F, SHARED_VECTOR_4F);
		float time = audio.getPosition();

		Print.out("Time:", time);
		Print.out("Prev:", prevPosition);

		if (!loop && time <= 0 && time < prevPosition)
		{
			Print.out("Stopping sound");
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
