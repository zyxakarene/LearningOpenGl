package zyx.game.controls.sound;

import java.util.LinkedList;
import org.newdawn.slick.openal.SoundStore;
import zyx.game.components.GameObject;
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
		playingSounds = new Sound[30];

		int len = playingSounds.length;
		for (int i = 0; i < len; i++)
		{
			availibleSounds.add(new Sound(i));
		}
		
		SoundStore.get().init();
	}

	public static SoundManager getInstance()
	{
		return INSTANCE;
	}

	public void playSound(String source, GameObject emitter)
	{
		if (emitter == null)
		{
			Print.out("Emitter playing sound", source, "is null. Not playing sound.");
			return;
		}
		
		if (availibleSounds.isEmpty())
		{
			Print.out("No availible sounds!");
			return;
		}

		Sound sound = availibleSounds.removeLast();
		AudioWrapper audio = new AudioWrapper(source);

		sound.set(10, false, audio, emitter);
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
		int len = playingSounds.length;
		Sound playingSound;
		for (int i = 0; i < len; i++)
		{
			playingSound = playingSounds[i];
			if (playingSound != null)
			{
				playingSound.update(timestamp, elapsedTime);
			}
		}
	}

	@Override
	public void dispose()
	{
		int len = playingSounds.length;
		Sound playingSound;
		for (int i = 0; i < len; i++)
		{
			playingSound = playingSounds[i];
			if (playingSound != null)
			{
				playingSound.dispose();
			}
		}

		availibleSounds.clear();
	}

}
