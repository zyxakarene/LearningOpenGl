package zyx.opengl.models.loading.skeletons;

import java.io.DataInputStream;
import java.io.IOException;
import zyx.utils.PrintBuilder;

class SkeletonObject
{

	BoneInfoObject boneInfo;
	BoneObject rootBone;
	AnimationObject[] animations;

	public void read(DataInputStream in, PrintBuilder builder) throws IOException
	{
		boneInfo = new BoneInfoObject();
		boneInfo.read(in);
		builder.append("↳", boneInfo.boneIds.length, "bones");
		
		rootBone = new BoneObject();
		rootBone.read(in);

		int animationLength = in.readInt();
		animations = new AnimationObject[animationLength];
		builder.append("↳", animations.length, "animations");
		for (int i = 0; i < animationLength; i++)
		{
			animations[i] = new AnimationObject();
			animations[i].read(in);
			builder.append("↳ Animation:", animations[i].name);
		}
	}
}
