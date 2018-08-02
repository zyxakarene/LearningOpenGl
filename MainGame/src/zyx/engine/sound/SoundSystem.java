package zyx.engine.sound;

import java.io.InputStream;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

public class SoundSystem
{

	public static final int MAX_SOURCES = 64;

	private static int[] sources;

	private static FloatBuffer listenerOrientation;
	private static FloatBuffer listenerVelocity;
	private static FloatBuffer listenerPos;

	private static float[] orientationData;
	private static float[] velocityData;
	private static float[] positionData;

	public static void initialize()
	{
		try
		{
			AL.create();
		}
		catch (LWJGLException ex)
		{
			throw new RuntimeException("Sounds does not work correct!", ex);
		}
		createSources();
		createFloatBuffers();
	}

	static int play(int buffer, float x, float y, float z, float gain, boolean loop)
	{
		float pitch = 1;
		float volume = 1;
		if (volume <= 0)
		{
			volume = 0.001f;
		}

		int sourceIndex = getNextFreeSourceIndex();
		if (sourceIndex <= -1)
		{
			return -1;
		}

		int source = sources[sourceIndex];

		AL10.alSourceStop(source);

		AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
		AL10.alSourcef(source, AL10.AL_PITCH, pitch);
		AL10.alSourcef(source, AL10.AL_GAIN, volume);
		AL10.alSourcei(source, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);

		listenerPos.clear();
		positionData[0] = x;
		positionData[1] = y;
		positionData[2] = z;
		listenerPos.put(positionData);
		listenerPos.flip();

		AL10.alSource(source, AL10.AL_POSITION, listenerPos);
		AL10.alSource(source, AL10.AL_VELOCITY, listenerVelocity);

		AL10.alSourcePlay(source);
		
		return sourceIndex;
	}

	static void stop(int sourceIndex)
	{
		int source = sources[sourceIndex];
		AL10.alSourceStop(source);
	}

	static void setPosition(int sourceIndex, float position)
	{
		int source = sources[sourceIndex];
		AL10.alSourcef(source, AL11.AL_SEC_OFFSET, position);
	}

	static void setListenerPosition(int sourceIndex, float x, float y, float z)
	{
		listenerPos.clear();
		positionData[0] = x;
		positionData[1] = y;
		positionData[2] = z;
		listenerPos.put(positionData);
		listenerPos.flip();

		int source = sources[sourceIndex];
		AL10.alSource(source, AL10.AL_POSITION, listenerPos);
	}

	static float getPosition(int sourceIndex)
	{
		int source = sources[sourceIndex];
		float pos = AL10.alGetSourcef(source, AL11.AL_SEC_OFFSET);

		return pos;
	}

	public static IAudio createFromWav(InputStream in, String path)
	{
		int bufferId = AudioCreator.createFromWav(in);

		Audio audio = new Audio(bufferId, path);
		return audio;
	}

	private static int getNextFreeSourceIndex()
	{
		int state;
		int source;
		for (int i = 0; i < MAX_SOURCES; i++)
		{
			source = sources[i];
			state = AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE);

			if (state != AL10.AL_PLAYING)
			{
				return i;
			}
		}

		return -1;
	}

	private static void createSources()
	{
		sources = new int[MAX_SOURCES];

		for (int i = 0; i < MAX_SOURCES; i++)
		{
			int source = AL10.alGenSources();
			sources[i] = source;
		}
	}

	private static void createFloatBuffers()
	{
		positionData = new float[]
		{
			0.0f, 0.0f, 0.0f
		};
		velocityData = new float[]
		{
			0.0f, 0.0f, 0.0f
		};
		orientationData = new float[]
		{
			0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f
		};

		listenerPos = BufferUtils.createFloatBuffer(3);
		listenerPos.put(positionData);

		listenerVelocity = BufferUtils.createFloatBuffer(3);
		listenerVelocity.put(velocityData);

		listenerOrientation = BufferUtils.createFloatBuffer(6);
		listenerOrientation.put(orientationData);

		listenerPos.flip();
		listenerVelocity.flip();
		listenerOrientation.flip();
		AL10.alListener(AL10.AL_POSITION, listenerPos);
		AL10.alListener(AL10.AL_VELOCITY, listenerVelocity);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOrientation);
	}

	public static void dispose()
	{
		for (int i = 0; i < sources.length; i++)
		{
			int source = sources[i];
			AL10.alDeleteSources(source);
		}

		AL.destroy();
	}

	private static void errorCheck()
	{
		int errorID;
		while ((errorID = AL10.alGetError()) != AL10.AL_NO_ERROR)
		{
			String msg = String.format("ALError: [%s]", errorID);
			throw new RuntimeException(msg);
		}
	}
}
