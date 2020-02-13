package zyx.opengl.models.loading.skeletons;

import java.io.IOException;
import java.util.logging.Level;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.bones.animation.Animation;
import zyx.opengl.models.implementations.bones.animation.AnimationFrame;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.bones.transform.JointTransform;
import zyx.utils.GameConstants;
import zyx.utils.math.MatrixUtils;

public class SkeletonLoader
{

	private static final Quaternion ROTATION = new Quaternion();
	private static final Vector3f POSITION = new Vector3f();

	public static Skeleton loadSkeletonFrom(ResourceDataInputStream in)
	{
		try
		{
			SkeletonObject obj = new SkeletonObject();
			obj.read(in);
			
			Joint rootJoint = getJointFrom(obj.rootBone, obj.boneInfo);
			Joint meshJoint = getMeshJoint();

			Skeleton skeleton = new Skeleton(rootJoint, meshJoint);
			addAnimationsTo(skeleton, obj.animations);

			return skeleton;
		}
		catch (IOException e)
		{
			GameConstants.LOGGER.log(Level.SEVERE, "Error at loading a skeleton data", e);
			return null;
		}
	}

	private static Joint getJointFrom(BoneObject bone, BoneInfoObject boneInfo)
	{
		POSITION.set(bone.restX, bone.restY, bone.restZ);
		ROTATION.set(bone.restRotX, bone.restRotY, bone.restRotZ, bone.restRotW);

		Matrix4f restPose = SharedPools.MATRIX_POOL.getInstance();
		MatrixUtils.transformMatrix(ROTATION, POSITION, restPose);

		String boneName = boneInfo.getBoneName(bone.id);
		Joint joint = new Joint(bone.id, boneName, restPose);

		for (BoneObject childBone : bone.children)
		{
			Joint childJoint = getJointFrom(childBone, boneInfo);
			joint.addChild(childJoint);
		}

		return joint;
	}

	private static void addAnimationsTo(Skeleton skeleton, AnimationObject[] smdAnimations)
	{
		for (AnimationObject smdAnimation : smdAnimations)
		{
			Animation jointAnim = new Animation(smdAnimation.name, smdAnimation.length, smdAnimation.looping);

			for (AnimationFrameObject smdFrame : smdAnimation.frames)
			{
				AnimationFrame jointFrame = new AnimationFrame();

				for (AnimationTransformObject smdTransform : smdFrame.transforms)
				{
					JointTransform jointTransform = new JointTransform(smdTransform.x, smdTransform.y, smdTransform.z,
																	   smdTransform.rotX, smdTransform.rotY, smdTransform.rotZ, smdTransform.rotW);
					jointFrame.addTransform(smdTransform.boneId, jointTransform);
				}

				jointAnim.setFrame(smdFrame.frame, jointFrame);
			}

			skeleton.addAnimation(smdAnimation.name, jointAnim);
		}
	}

	private static Joint getMeshJoint()
	{
		String name = "dummy";
		Matrix4f matrix = SharedPools.MATRIX_POOL.getInstance();
		byte id = 0;

		return new Joint(id, name, matrix);
	}
}
