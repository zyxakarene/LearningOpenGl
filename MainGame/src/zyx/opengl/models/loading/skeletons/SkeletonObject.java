package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;
import zyx.utils.cheats.Print;

class SkeletonObject
{

	BoneInfoObject boneInfo;
	BoneObject rootBone;
	AnimationObject[] animations;

	public void read(DataInputStream in) throws IOException
	{
		Print.out("Parsing model data from byte count:", in.available());

		boneInfo = new BoneInfoObject();
		boneInfo.read(in);
		
		rootBone = new BoneObject();
		rootBone.read(in);

		int animationLength = in.readInt();
		animations = new AnimationObject[animationLength];
		Print.out("↳", animations.length, "animations");
		for (int i = 0; i < animationLength; i++)
		{
			animations[i] = new AnimationObject();
			animations[i].read(in);
		}
	}
}
