package zyx.engine.resources.impl;

import java.io.IOException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.NullAudio;
import org.newdawn.slick.openal.SoundStore;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.utils.cheats.Print;

public class SoundResource extends Resource
{

	private Audio audio;

	public SoundResource(String path)
	{
		super(path);
	}

	@Override
	public Audio getContent()
	{
		return audio;
	}

	@Override
	public void resourceLoaded(ResourceDataInputStream data)
	{
		try
		{
			SoundStore soundStore = SoundStore.get();
			audio = soundStore.getWAV(data);
		}
		catch (IOException ex)
		{
			Print.out(ex.getMessage());
			audio = new NullAudio();
		}
		
		onContentLoaded(audio);
	}

}
