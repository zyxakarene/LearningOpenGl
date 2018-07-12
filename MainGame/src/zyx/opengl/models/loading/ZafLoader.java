package zyx.opengl.models.loading;

import java.io.IOException;
import java.util.logging.Level;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.game.controls.resourceloader.requests.vo.ResourceDataInputStream;
import zyx.opengl.models.implementations.LoadableWorldModelVO;

import zyx.opengl.models.implementations.bones.animation.Animation;
import zyx.opengl.models.implementations.bones.animation.AnimationFrame;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.bones.transform.JointTransform;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.utils.GameConstants;
import zyx.utils.math.MatrixUtils;

public class ZafLoader
{

	private static final Quaternion ROTATION = new Quaternion();
	private static final Vector3f POSITION = new Vector3f();

	public static LoadableWorldModelVO loadFromZaf(ResourceDataInputStream in)
	{
		try
		{
			SmdObject smd = new SmdObject();
			smd.read(in);

			Joint rootJoint = getJointFrom(smd.rootBone);
			Joint meshJoint = getMeshJoint();

			Skeleton skeleton = new Skeleton(rootJoint, meshJoint);
			addAnimationsTo(skeleton, smd.animations);

			PhysBox phys = createPhysBox(smd.physInformation);

			return new LoadableWorldModelVO(smd.vertexData, smd.elementData, skeleton, phys, smd.texture);
		}
		catch (IOException e)
		{
			GameConstants.LOGGER.log(Level.SEVERE, "Error at loading a zaf data", e);
			return null;
		}
	}

	private static Joint getJointFrom(SmdBone bone)
	{
		POSITION.set(bone.restX, bone.restY, bone.restZ);
		ROTATION.set(bone.restRotX, bone.restRotY, bone.restRotZ, bone.restRotW);

		Matrix4f restPose = SharedPools.MATRIX_POOL.getInstance();
		MatrixUtils.transformMatrix(ROTATION, POSITION, restPose);

		Joint joint = new Joint(bone.id, bone.name, restPose);

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
					JointTransform jointTransform = new JointTransform(smdTransform.x, smdTransform.y, smdTransform.z,
																	   smdTransform.rotX, smdTransform.rotY, smdTransform.rotZ, smdTransform.rotW);
					jointFrame.addTransform(smdTransform.name, jointTransform);
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
		int id = 0;

		return new Joint(id, name, matrix);
	}

	private static PhysBox createPhysBox(SmdPhysInfo physInfo)
	{
		SmdPhysbox[] boxes = physInfo.physBoxes;
		int triangleCount = getTriangleCount(boxes);

		PhysBox box = new PhysBox(triangleCount, physInfo.boundingBox, boxes.length);
		for (SmdPhysbox physBox : boxes)
		{
			box.addObject(physBox.triangles.length, physBox.boneId);

			for (SmdPhysTriangle triangle : physBox.triangles)
			{
				box.addTriangle(triangle.v1, triangle.v2, triangle.v3, triangle.normal);
			}
		}

		return box;
	}

	private static int getTriangleCount(SmdPhysbox[] physboxes)
	{
		int count = 0;

		for (SmdPhysbox box : physboxes)
		{
			count += box.triangles.length;
		}

		return count;
	}
}
