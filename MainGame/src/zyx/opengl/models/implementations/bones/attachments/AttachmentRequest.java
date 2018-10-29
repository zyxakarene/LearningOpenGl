package zyx.opengl.models.implementations.bones.attachments;

import zyx.game.components.SimpleMesh;

public class AttachmentRequest
{
	public SimpleMesh child;
	public String attachmentPoint;

	public AttachmentRequest(SimpleMesh child, String attachmentPoint)
	{
		this.child = child;
		this.attachmentPoint = attachmentPoint;
	}
}
