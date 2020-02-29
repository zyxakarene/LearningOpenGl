package zyx.debug.link;

import zyx.engine.sound.DebugSound;
import zyx.engine.sound.SoundSystem;

public class DebugSoundLink
{
	private final DebugSound[] SOURCES = new DebugSound[SoundSystem.MAX_SOURCES];

	DebugSoundLink()
	{
		for (int i = 0; i < SoundSystem.MAX_SOURCES; i++)
		{
			SOURCES[i] = new DebugSound();
		}
	}
	
	public void setSourceStatus(int index, boolean status, String path, float x, float y, float z)
	{
		synchronized(SOURCES)
		{
			DebugSound sound = SOURCES[index];
			sound.status = status;
			sound.path = path;
			sound.position.x = x;
			sound.position.y = y;
			sound.position.z = z;
		}
	}
	
	public void getSourceStatus(DebugSound[] out)
	{
		synchronized(SOURCES)
		{
			System.arraycopy(SOURCES, 0, out, 0, out.length);
		}
	}
}
