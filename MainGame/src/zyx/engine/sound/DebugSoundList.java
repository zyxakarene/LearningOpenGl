package zyx.engine.sound;

public class DebugSoundList
{
	private static final DebugSound[] SOURCES = new DebugSound[SoundSystem.MAX_SOURCES];
		
	static
	{
		for (int i = 0; i < SoundSystem.MAX_SOURCES; i++)
		{
			SOURCES[i] = new DebugSound();
		}
	}
	
	static void setSourceStatus(int index, boolean status, String path, float x, float y, float z)
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
	
	public static void getSourceStatus(DebugSound[] out)
	{
		synchronized(SOURCES)
		{
			System.arraycopy(SOURCES, 0, out, 0, out.length);
		}
	}
}
