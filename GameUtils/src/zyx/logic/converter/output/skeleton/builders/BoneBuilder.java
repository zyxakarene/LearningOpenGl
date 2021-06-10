package zyx.logic.converter.output.skeleton.builders;

import java.util.ArrayList;
import java.util.HashMap;
import zyx.logic.UtilsLogger;
import zyx.logic.converter.output.skeleton.SkeletonBone;
import zyx.logic.converter.output.skeleton.SkeletonBoneInfo;
import zyx.logic.converter.output.skeleton.SkeletonMeshVo;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdBone;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFrame;

public class BoneBuilder
{

	private final ParsedSmdFile skeletonRef;

	public BoneBuilder(ParsedSmdFile skeletonRef)
	{
		this.skeletonRef = skeletonRef;
	}

	public void addTo(SkeletonMeshVo vo)
	{
		ArrayList<SkeletonBoneInfo> boneInfo = new ArrayList<>();

		for (int i = 0; i < skeletonRef.bones.size(); i++)
		{
			ParsedSmdBone bone = skeletonRef.bones.get(i);

			SkeletonBoneInfo skeletonBoneInfo = new SkeletonBoneInfo();
			skeletonBoneInfo.name = bone.name;
			skeletonBoneInfo.id = bone.id;

			boneInfo.add(skeletonBoneInfo);
		}

		SkeletonBone rootBone = createRootBone();

		vo.boneInfo = boneInfo;
		vo.rootBone = rootBone;
	}

	private SkeletonBone createRootBone()
	{
		HashMap<Byte, SkeletonBone> skeletonBoneById = new HashMap<>();

		for (ParsedSmdBone bone : skeletonRef.bones)
		{
			SkeletonBone child = new SkeletonBone();

			child.id = bone.id;
			getRestInfoTo(child);

			SkeletonBone parent = skeletonBoneById.get(bone.parentId);
			if (parent != null)
			{
				UtilsLogger.log("creating bone " + bone.id + " with parent " + parent.id);
				parent.childBones.add(child);
			}
			else
			{
				UtilsLogger.log("creating bone " + bone.id + " with no parent");
			}

			skeletonBoneById.put(child.id, child);
		}

		SkeletonBone rootBone = skeletonBoneById.get((byte) 1);
		if (rootBone == null)
		{
			UtilsLogger.log("No root bone found. Creating default!");
			rootBone = new SkeletonBone();
			rootBone.id = 1;
		}
		
		return rootBone;
	}

	private void getRestInfoTo(SkeletonBone bone)
	{
		ParsedSmdFrame restFrame = skeletonRef.frames.get(0);

		for (int i = 0; i < restFrame.boneIds.size(); i++)
		{
			byte restBoneId = restFrame.boneIds.get(i);
			if (restBoneId == bone.id)
			{
				bone.restPos.set(restFrame.positions.get(i));
				bone.restRot.set(restFrame.rotations.get(i));
				return;
			}
		}
	}

}
