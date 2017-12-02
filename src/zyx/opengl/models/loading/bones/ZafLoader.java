package zyx.opengl.models.loading.bones;

import java.io.*;
import zyx.opengl.models.implementations.BoneModel;

import zyx.opengl.models.implementations.bones.animation.Animation;
import zyx.opengl.models.implementations.bones.animation.AnimationFrame;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.bones.transform.JointTransform;

public class ZafLoader
{

	public static BoneModel loadFromZaf(String name)
	{
		long start = System.currentTimeMillis();
		try
		{
			File input = new File("assets/models/" + name);

			RandomAccessFile raf = new RandomAccessFile(input, "r");
			byte[] buffer = new byte[(int) raf.length()];
			raf.read(buffer, 0, buffer.length);
			
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(buffer));
			
			SmdObject smd = new SmdObject();
			smd.read(in);

			Joint rootJoint = getJointFrom(smd.rootBone);
			
			Skeleton skeleton = new Skeleton(rootJoint);
			addAnimationsTo(skeleton, smd.animations);
			
			BoneModel result = new BoneModel(smd.triangleData, smd.elementData, skeleton);
			
			long end = System.currentTimeMillis();
			System.out.println("Took " + (end - start) + "ms to load " + name);

			raf.close();
			
			return result;

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private static Joint getJointFrom(SmdBone bone)
	{
		JointTransform transform = new JointTransform(bone.restX, bone.restY, bone.restZ, bone.restRotX, bone.restRotY, bone.restRotZ);
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
					JointTransform jointTransform = new JointTransform(smdTransform.x, smdTransform.y, smdTransform.z, smdTransform.rotX, smdTransform.rotY, smdTransform.rotZ);
					jointFrame.addTransform(smdTransform.name, jointTransform);
				}
				
				jointAnim.setFrame(smdFrame.frame, jointFrame);
			}
			
			skeleton.addAnimation(smdAnimation.name, jointAnim);
		}
	}
}
