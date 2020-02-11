package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;

class SmdAnimation
{
	String name;
	int length;
	boolean looping;
	SmdAnimationFrame[] frames;

	SmdAnimation()
	{
	}
	
	void read(DataInputStream in) throws IOException
	{
		name = in.readUTF();
		looping = in.readBoolean();
		length = in.readInt();
		frames = new SmdAnimationFrame[length];
		
		for (int i = 0; i < length; i++)
		{
			frames[i] = new SmdAnimationFrame();
			frames[i].read(in);
		}
	}
}
