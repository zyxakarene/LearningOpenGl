package zyx.engine.sound;

import org.lwjgl.openal.AL10;
import zyx.debug.link.DebugInfo;

class Audio implements IAudio
{

	private static final String EMPTY_STRING = "";
	
	private int sourceIndex;
	private boolean playing;

	private int buffer;
	private float length;

	private boolean clone;
	private String path;

	private Audio(int buffer, float length, String path)
	{
		this.buffer = buffer;
		this.length = length;
		this.path = path;
		this.clone = true;
	}

	Audio(int buffer, String path)
	{
		this.buffer = buffer;
		this.clone = false;
		this.path = path;

		int bytes = AL10.alGetBufferi(buffer, AL10.AL_SIZE);
		int bits = AL10.alGetBufferi(buffer, AL10.AL_BITS);
		int channels = AL10.alGetBufferi(buffer, AL10.AL_CHANNELS);
		int freq = AL10.alGetBufferi(buffer, AL10.AL_FREQUENCY);

		int samples = bytes / (bits / 8);
		length = (samples / (float) freq) / channels;
	}

	@Override
	public Audio createClone()
	{
		return new Audio(buffer, length, path);
	}

	@Override
	public void dispose()
	{
		stop();

		if (!clone)
		{
			AL10.alDeleteBuffers(buffer);
		}
	}

	@Override
	public void playAt(float x, float y, float z, float volume, boolean loop)
	{
		sourceIndex = SoundSystem.play(buffer, x, y, z, volume, loop);
		playing = sourceIndex != -1;

		if (playing)
		{
			DebugInfo.sounds.setSourceStatus(sourceIndex, true, path, x, y, z);
		}
	}

	@Override
	public boolean isPlaying()
	{
		return playing;
	}

	@Override
	public void stop()
	{
		if (playing)
		{
			DebugInfo.sounds.setSourceStatus(sourceIndex, false, EMPTY_STRING, 0, 0, 0);
			
			SoundSystem.stop(sourceIndex);
			sourceIndex = -1;
			playing = false;
		}
	}

	@Override
	public void setPosition(float position)
	{
		if (playing)
		{
			position = position % length;
			SoundSystem.setPosition(sourceIndex, position);
		}
	}

	@Override
	public float getPosition()
	{
		if (playing)
		{
			return SoundSystem.getPosition(sourceIndex);
		}
		else
		{
			return 0;
		}
	}

	@Override
	public void setListenerPosition(float x, float y, float z)
	{
		if (playing)
		{
			SoundSystem.setListenerPosition(sourceIndex, x, y, z);
			DebugInfo.sounds.setSourceStatus(sourceIndex, true, path, x, y, z);
		}
	}

}
