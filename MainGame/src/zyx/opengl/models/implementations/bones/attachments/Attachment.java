package zyx.opengl.models.implementations.bones.attachments;

import org.lwjgl.util.vector.Matrix4f;
import zyx.game.components.SimpleMesh;
import zyx.opengl.models.implementations.bones.skeleton.Joint;

public class Attachment
{
	public Joint joint;
	public Matrix4f lastMatrix;
	public SimpleMesh parent;
	public SimpleMesh child;

	public Attachment()
	{
		this.lastMatrix = new Matrix4f();
	}

	public void loadBoneTransform()
	{
		lastMatrix.load(joint.getAttachmentTransform());
	}
	
}
