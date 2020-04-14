package zyx.game.controls.sound;

import java.util.LinkedList;
import org.lwjgl.util.vector.Vector3f;
import zyx.engine.components.world.WorldObject;
import zyx.engine.sound.SoundSystem;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class SoundManager implements IUpdateable, IDisposeable
{

	private static final SoundManager INSTANCE = new SoundManager();

	private LinkedList<Sound> availibleSounds;
	private Sound[] playingSounds;

	private SoundManager()
	{
		availibleSounds = new LinkedList<>();
		playingSounds = new Sound[SoundSystem.MAX_SOURCES];

		int len = playingSounds.length;
		for (int i = 0; i < len; i++)
		{
			availibleSounds.add(new Sound(i));
		}
	}

	public static SoundManager getInstance()
	{
		return INSTANCE;
	}

	public void playSound(String resource, WorldObject emitter)
	{
		Sound sound = getSound();
		
		if (sound != null)
		{
			sound.set(10, false, resource, emitter);
		}
	}
	
	public void playSound(String resource, Vector3f position)
	{
		Sound sound = getSound();
		
		if (sound != null)
		{
			sound.set(10, false, resource, position);
		}
	}
	
	private Sound getSound()
	{
		if (availibleSounds.isEmpty())
		{
			Print.out("No availible sounds!");
			return null;
		}

		Sound sound = availibleSounds.removeFirst();
		playingSounds[sound.soundId] = sound;
		
		return sound;
	}

	void onSoundDone(Sound sound)
	{
		sound.dispose();
		
		playingSounds[sound.soundId] = null;
		availibleSounds.add(sound);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		for (Sound playingSound : playingSounds)
		{
			if (playingSound != null)
			{
				playingSound.update(timestamp, elapsedTime);
			}
		}
	}

	@Override
	public void dispose()
	{
		for (Sound playingSound : playingSounds)
		{
			if (playingSound != null)
			{
				playingSound.dispose();
			}
		}
		availibleSounds.clear();
	}

}
