package zyx.logic.converter.output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import zyx.logic.converter.output.skeleton.*;
import zyx.logic.converter.smd.control.json.JsonMesh;
import zyx.logic.converter.smd.control.json.JsonMeshAnimation;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdBone;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFile;
import zyx.logic.converter.smdV2.parsedVo.ParsedSmdFrame;

public class SkeletonOutputGenerator extends AbstractOutputGenerator
{

	protected final ParsedSmdFile skeletonRef;

	public SkeletonOutputGenerator(JsonMesh mesh)
	{
		super(mesh);

		skeletonRef = tryParse(mesh.skeletonMesh);
	}

	public SkeletonMeshVo getSkeleton() throws IOException
	{
		SkeletonMeshVo vo = new SkeletonMeshVo();

		fillBones(vo);
		fillAnimations(vo);

		return vo;
	}

	private void fillAnimations(SkeletonMeshVo vo)
	{
		for (JsonMeshAnimation jsonAnimation : animationToFile.keySet())
		{
			ParsedSmdFile file = animationToFile.get(jsonAnimation);

			SkeletonAnimation anim = new SkeletonAnimation();
			anim.blendTime = jsonAnimation.blend;
			anim.looping = jsonAnimation.loop;
			anim.name = jsonAnimation.name;

			short start = jsonAnimation.framesStart;
			short end = jsonAnimation.framesEnd;
			for (short i = start; i <= end; i++)
			{
				ParsedSmdFrame fileFrame = file.frames.get(i);
				SkeletonAnimationFrame skeletonFrame = new SkeletonAnimationFrame();
				skeletonFrame.frame = (short) (i - start);
				for (int j = 0; j < fileFrame.boneIds.size(); j++)
				{
					SkeletonAnimationTransform frameTransform = new SkeletonAnimationTransform();
					frameTransform.boneId = fileFrame.boneIds.get(j);
					frameTransform.position.set(fileFrame.positions.get(j));
					frameTransform.rotation.set(fileFrame.rotations.get(j));

					skeletonFrame.transforms.add(frameTransform);
				}

				anim.frames.add(skeletonFrame);
			}

			vo.animations.add(anim);
		}
	}

	private void fillBones(SkeletonMeshVo vo)
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
				parent.childBones.add(child);
			}

			skeletonBoneById.put(child.id, child);
		}

		return skeletonBoneById.get((byte) 1);
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
