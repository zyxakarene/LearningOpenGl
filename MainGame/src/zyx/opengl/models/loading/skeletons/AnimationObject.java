package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;

class AnimationObject
{
	String name;
	short length;
	boolean looping;
	AnimationFrameObject[] frames;

	AnimationObject()
	{
	}
	
	void read(DataInputStream in) throws IOException
	{
		name = in.readUTF();
		looping = in.readBoolean();
		length = in.readShort();
		frames = new AnimationFrameObject[length];
		
		for (int i = 0; i < length; i++)
		{
			frames[i] = new AnimationFrameObject();
			frames[i].read(in);
		}
	}
}
