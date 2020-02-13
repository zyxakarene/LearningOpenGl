package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;

class AnimationFrameObject
{

	short frame;
	AnimationTransformObject[] transforms;

	AnimationFrameObject()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		
		frame = in.readShort();
		short length = in.readShort();
		transforms = new AnimationTransformObject[length];
		
		for (int i = 0; i < length; i++)
		{
			transforms[i] = new AnimationTransformObject();
			transforms[i].read(in);
		}
	}
}
