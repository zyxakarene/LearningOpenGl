package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;
import zyx.utils.cheats.Print;

class SkeletonObject
{

	SmdBone rootBone;
	SmdAnimation[] animations;

	public void read(DataInputStream in) throws IOException
	{
		Print.out("Parsing model data from byte count:", in.available());

		rootBone = new SmdBone();
		rootBone.read(in);

		int animationLength = in.readInt();
		animations = new SmdAnimation[animationLength];
		Print.out("↳", animations.length, "animations");
		for (int i = 0; i < animationLength; i++)
		{
			animations[i] = new SmdAnimation();
			animations[i].read(in);
		}
	}
}
