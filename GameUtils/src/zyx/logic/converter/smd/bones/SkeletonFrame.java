package zyx.logic.converter.smd.bones;

import java.util.ArrayList;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class SkeletonFrame
{

	private final int time;
	private final ArrayList<FrameChange> changes;
	
	private Matrix4f mat;

	public SkeletonFrame(int time)
	{
		this.time = time;
		this.changes = new ArrayList<>();
	}
	
	private static final Vector3f ROTATION_PITCH = new Vector3f(1.0f, 0.0f, 0.0f);
    private static final Vector3f ROTATION_ROLL = new Vector3f(0.0f, 1.0f, 0.0f);
    private static final Vector3f ROTATION_YAW = new Vector3f(0.0f, 0.0f, 1.0f);
	public void addChange(int nodeId, Vector3f pos, Vector3f rotation, Matrix4f matrix)
	{
		this.mat = matrix;
		
		FrameChange change = new FrameChange();
		change.nodeId = nodeId;
		change.x = pos.x;
		change.y = pos.y;
		change.z = pos.z;
		change.rotX = rotation.x;
		change.rotY = rotation.y;
		change.rotZ = rotation.z;
		
		changes.add(change);
	}

	public void handleNodes(INode[] nodes)
	{
		for (int i = 0; i < changes.size(); i++)
		{
			FrameChange change = changes.get(i);
			change.node = getNodeFromId(nodes, change.nodeId);
		}
	}

	private INode getNodeFromId(INode[] nodes, int nodeId)
	{
		for (int i = 0; i < nodes.length; i++)
		{
			INode node = nodes[i];
			if (node.getNodeId() == nodeId)
			{
				return node;
			}
		}
		
		return null;
	}

	public void updateBones()
	{
		for (int i = 0; i < changes.size(); i++)
		{
			doUpdateBone(changes.get(i));
		}
	}

	private Vector3f pos = new Vector3f();
	private Vector3f rot = new Vector3f();
	private Vector3f scale = new Vector3f();

	private static Matrix4f posMat = new Matrix4f();
	private static Matrix4f rotX = new Matrix4f();
	private static Matrix4f rotY = new Matrix4f();
	private static Matrix4f rotZ = new Matrix4f();
	private static Matrix4f scaleMat = new Matrix4f();
	
	private Matrix4f tempBone;
	private void doUpdateBone(FrameChange bone)
	{
		if (mat == null)
		{
			return;
		}
		
//		tempBone = BoneShader.BONES[bone.nodeId];
//		tempBone.setIdentity();
//		
//		pos.set(bone.x, bone.y, bone.z);
//		rot.set(bone.rotX, bone.rotY, bone.rotZ);
//		scale.set(1, 1, 1);
//
//		mat.scale(scale);
//		mat.translate(pos);
//		mat.rotate((float) (rot.z), ROTATION_YAW);
//		mat.rotate((float) (rot.y), ROTATION_ROLL);
//		mat.rotate((float) (rot.x), ROTATION_PITCH);
	}

	private class FrameChange
	{
		public INode node;
		public int nodeId;
		public float x, y, z;
		public float rotX, rotY, rotZ;
	}
}
