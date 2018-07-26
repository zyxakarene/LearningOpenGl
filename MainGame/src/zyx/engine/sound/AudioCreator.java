package zyx.engine.sound;

import java.io.InputStream;
import org.lwjgl.openal.AL10;
import org.newdawn.slick.openal.WaveData;

class AudioCreator
{

	static int createFromWav(InputStream in)
	{
		WaveData data = WaveData.create(in);

		int buffer = AL10.alGenBuffers();
		AL10.alBufferData(buffer, data.format, data.data, data.samplerate);

		data.dispose();

		return buffer;
	}
}
