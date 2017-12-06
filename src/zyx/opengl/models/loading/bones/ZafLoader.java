package zyx.opengl.models.loading.bones;

import java.io.*;
import java.util.logging.Level;
import zyx.opengl.models.implementations.LoadableValueObject;

import zyx.opengl.models.implementations.bones.animation.Animation;
import zyx.opengl.models.implementations.bones.animation.AnimationFrame;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.bones.transform.JointTransform;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;

public class ZafLoader
{

	public static LoadableValueObject loadFromZaf(DataInputStream in)
	{
		try
		{			
			SmdObject smd = new SmdObject();
			smd.read(in);
			
			Joint rootJoint = getJointFrom(smd.rootBone);
			
			Skeleton skeleton = new Skeleton(rootJoint);
			addAnimationsTo(skeleton, smd.animations);
			
			return new LoadableValueObject(smd.triangleData, smd.elementData, skeleton, "knight");
		}
		catch (IOException e)
		{
			GameConstants.LOGGER.log(Level.SEVERE, "Error at loading a zaf data", e);
			return null;
		}
	}

	private static Joint getJointFrom(SmdBone bone)
	{
		JointTransform transform = new JointTransform(bone.restX, bone.restY, bone.restZ, bone.restRotX, bone.restRotY, bone.restRotZ, bone.restRotW);
		Joint joint = new Joint(bone.id, bone.name, transform);
		
		for (SmdBone childBone : bone.children)
		{
			Joint childJoint = getJointFrom(childBone);
			joint.addChild(childJoint);
		}
		
		return joint;
	}

	private static void addAnimationsTo(Skeleton skeleton, SmdAnimation[] smdAnimations)
	{
		for (SmdAnimation smdAnimation : smdAnimations)
		{
			Animation jointAnim = new Animation(smdAnimation.name, smdAnimation.length);
			
			for (SmdAnimationFrame smdFrame : smdAnimation.frames)
			{
				AnimationFrame jointFrame = new AnimationFrame();
				
				for (SmdAnimationTransform smdTransform : smdFrame.transforms)
				{
					JointTransform jointTransform = new JointTransform(smdTransform.x,	  smdTransform.y,    smdTransform.z, 
																	   smdTransform.rotX, smdTransform.rotY, smdTransform.rotZ, smdTransform.rotW);
					jointFrame.addTransform(smdTransform.name, jointTransform);
				}
				
				jointAnim.setFrame(smdFrame.frame, jointFrame);
			}
			
			skeleton.addAnimation(smdAnimation.name, jointAnim);
		}
	}
}
