package zyx.logic.converter.output.skeleton;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import zyx.logic.converter.output.ISaveable;

public class SkeletonMeshVo implements ISaveable
{

	public ArrayList<SkeletonBoneInfo> boneInfo;
	public SkeletonBone rootBone;
	public ArrayList<SkeletonAnimation> animations;

	public SkeletonMeshVo()
	{
		boneInfo = new ArrayList<>();
		rootBone = new SkeletonBone();
		animations = new ArrayList<>();
	}

	@Override
	public void save(DataOutputStream out) throws IOException
	{
		out.writeByte(boneInfo.size());
		for (SkeletonBoneInfo bone : boneInfo)
		{
			bone.save(out);
		}
		
		rootBone.save(out);
		
		out.writeInt(animations.size());
		for (SkeletonAnimation animation : animations)
		{
			animation.save(out);
		}
	}
}
