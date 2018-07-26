package zyx.engine.resources.impl;

import zyx.engine.sound.IAudio;
import zyx.engine.sound.SoundSystem;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;

public class SoundResource extends Resource
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
		audio = SoundSystem.createFromWav(data);
		onContentLoaded(audio);
	}

	@Override
	void onDispose()
	{
		if(audio != null)
		{
			audio.dispose();
			audio = null;
		}
	}

}
