package zyx.opengl.models.loading.bones;

import java.io.DataInputStream;
import java.io.IOException;

class SmdAnimationFrame
{

	int frame;
	SmdAnimationTransform[] transforms;

	SmdAnimationFrame()
	{
	}

	void read(DataInputStream in) throws IOException
	{
		
		frame = in.readInt();
		int length = in.readInt();
		transforms = new SmdAnimationTransform[length];
		
		for (int i = 0; i < length; i++)
		{
			transforms[i] = new SmdAnimationTransform();
			transforms[i].read(in);
		}
	}
}
