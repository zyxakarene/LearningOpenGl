package zyx.game.controls.sound;

import java.util.LinkedList;
import org.newdawn.slick.openal.SoundStore;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.interfaces.IUpdateable;

public class SoundManager implements IUpdateable, IDisposeable
{

	private static final SoundManager instance = new SoundManager();

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
	}

	public static SoundManager getInstance()
	{
		return instance;
	}

	public void playSound(String source, IPositionable emitter)
	{
		if (availibleSounds.isEmpty())
		{
			Print.out("No availible sounds!");
			return;
		}

		Sound sound = availibleSounds.removeLast();
		AudioWrapper audio = new AudioWrapper(source);

		sound.set(1, true, audio, emitter);
		playingSounds[sound.soundId] = sound;
	}

	void onSoundDone(Sound sound)
	{
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

		SoundStore.get().poll(elapsedTime);
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
