package zyx.game.controls.sound;

import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.openal.Audio;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.resources.impl.SoundResource;
import zyx.utils.interfaces.IDisposeable;

class AudioWrapper implements IResourceReady<SoundResource>, IDisposeable
{

	private Audio audio;
	private float volume;
	private Vector4f savedPosition;

	private Resource soundResource;

	AudioWrapper(String resource)
	{
		soundResource = ResourceManager.getInstance().getResource(resource);
		soundResource.registerAndLoad(this);
	}

	@Override
	public void onResourceReady(SoundResource resource)
	{
		audio = resource.getContent();

		if (savedPosition != null)
		{
			playAsSoundEffect(volume, savedPosition);
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

	@Override
	public void dispose()
	{
		stop();
		
		if(soundResource != null)
		{
			soundResource.unregister(this);
			soundResource = null;
		}
	}
}
