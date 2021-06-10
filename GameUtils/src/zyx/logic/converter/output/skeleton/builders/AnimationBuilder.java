package zyx.logic.converter.output.skeleton.builders;

import java.util.HashMap;
import zyx.logic.converter.output.skeleton.SkeletonAnimation;
import zyx.logic.converter.output.skeleton.SkeletonAnimationFrame;
import zyx.logic.converter.output.skeleton.SkeletonAnimationTransform;
import zyx.logic.converter.output.skeleton.SkeletonMeshVo;
import zyx.logic.converter.json.JsonMeshAnimation;
import zyx.logic.converter.smd.parsedVo.ParsedSmdFile;
import zyx.logic.converter.smd.parsedVo.ParsedSmdFrame;

public class AnimationBuilder
{

	private HashMap<JsonMeshAnimation, ParsedSmdFile> animationToFile;

	public AnimationBuilder(HashMap<JsonMeshAnimation, ParsedSmdFile> animationToFile)
	{
		this.animationToFile = animationToFile;
	}

	public void addTo(SkeletonMeshVo vo)
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
			
			if (jsonAnimation.isFullFileAnimation())
			{
				start = 0;
				end = (short) (file.frames.size() - 1);
			}
			
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

}
