package zyx.game.controls.sound;

import org.lwjgl.util.vector.Vector4f;
import zyx.engine.resources.IResourceReady;
import zyx.engine.resources.ResourceManager;
import zyx.engine.resources.impl.Resource;
import zyx.engine.resources.impl.SoundResource;
import zyx.engine.sound.IAudio;
import zyx.utils.interfaces.IDisposeable;

class AudioWrapper implements IResourceReady<SoundResource>, IDisposeable
{

	private IAudio audio;
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
			audio.playAt(pos.x, pos.y, pos.z, volume, false);
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
		if (audio != null)
		{
			audio.setListenerPosition(position.x, position.y, position.z);
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
