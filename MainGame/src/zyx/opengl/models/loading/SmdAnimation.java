package zyx.opengl.models.loading;

import java.io.DataInputStream;
import java.io.IOException;

class SmdAnimation
{
	String name;
	int length;
	SmdAnimationFrame[] frames;

	SmdAnimation()
	{
	}
	
	void read(DataInputStream in) throws IOException
	{
		name = in.readUTF();
		length = in.readInt();
		frames = new SmdAnimationFrame[length];
		
		for (int i = 0; i < length; i++)
		{
			frames[i] = new SmdAnimationFrame();
			frames[i].read(in);
		}
	}
}
