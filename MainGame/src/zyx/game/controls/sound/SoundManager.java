package zyx.game.controls.sound;

import java.util.LinkedList;
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
		if (emitter == null)
		{
			Print.out("Emitter playing sound", resource, "is null. Not playing sound.");
			return;
		}
		
		if (availibleSounds.isEmpty())
		{
			Print.out("No availible sounds!");
			return;
		}

		Sound sound = availibleSounds.removeFirst();
		sound.set(10, false, resource, emitter);
		
		playingSounds[sound.soundId] = sound;
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
