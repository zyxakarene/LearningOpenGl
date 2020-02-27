package zyx.engine.resources.impl;

import zyx.engine.sound.IAudio;
import zyx.engine.sound.SoundSystem;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

public class SoundResource extends ExternalResource
{

	private IAudio audio;

	public SoundResource(String path)
	{
		super(path);
	}

	@Override
	public IAudio getContent()
	{
		return audio.createClone();
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		audio = SoundSystem.createFromWav(data, path);
		onContentLoaded(audio);
	}

	@Override
	protected void onDispose()
	{
		if(audio != null)
		{
			audio.dispose();
			audio = null;
		}
	}
	
	@Override
	public String getDebugIcon()
	{
		return "sound.png";
	}

}
