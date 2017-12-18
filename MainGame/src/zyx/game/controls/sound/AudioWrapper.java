package zyx.game.controls.sound;

import java.io.IOException;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import zyx.game.controls.resourceloader.ResourceLoader;
import zyx.game.controls.resourceloader.requests.IResourceLoaded;
import zyx.game.controls.resourceloader.requests.ResourceRequest;
import zyx.game.controls.resourceloader.requests.ResourceRequestByteArray;
import zyx.game.controls.resourceloader.requests.vo.ResourceByteArray;
import zyx.utils.exceptions.Msg;

class AudioWrapper implements IResourceLoaded<ResourceByteArray>
{

	private Audio audio;
	private String path;
	private float volume;
	private Vector4f savedPosition;

	AudioWrapper(String audioPath)
	{
		this.path = audioPath;

		ResourceByteArray data = AudioCache.getFromCache(path);
		if (data == null)
		{
			ResourceRequest audioRequest = new ResourceRequestByteArray(path, this);
			ResourceLoader.getInstance().addRequest(audioRequest);
		}
		else
		{
			createAudioFrom(data);
		}
	}

	@Override
	public void resourceLoaded(ResourceByteArray data)
	{
		AudioCache.addToCache(path, data);
		createAudioFrom(data);
	}

	private void createAudioFrom(ResourceByteArray data)
	{
		try
		{
			audio = AudioLoader.getAudio("WAV", data);

			if (savedPosition != null)
			{
				playAsSoundEffect(volume, savedPosition);
			}
		}
		catch (IOException ex)
		{
			Msg.error("Could not play sound!", ex);
		}
	}

	boolean isPlaying()
	{
		if (audio != null)
		{
			return audio.isPlaying();
		}

		return false;
	}

	float getPosition()
	{
		if (audio != null)
		{
			return audio.getPosition();
		}
		return 0;
	}

	void stop()
	{
		if (audio != null)
		{
			audio.stop();
		}
	}

	void playAsSoundEffect(float volume, Vector4f pos)
	{
		this.volume = volume;

		if (audio != null)
		{
			audio.playAsSoundEffect(1, volume, false, pos.x, pos.y, pos.z);
		}
		else
		{
			this.savedPosition = pos;
		}
	}

	void setPosition(float position)
	{
		if (audio != null)
		{
			audio.setPosition(position);
		}
	}

	void updatePosition(Vector4f position)
	{
		float time = getPosition();

		if (audio != null)
		{
			audio.stop();
			audio.playAsSoundEffect(1, volume, false, position.x, position.y, position.z);
			audio.setPosition(time);
		}
	}
}
