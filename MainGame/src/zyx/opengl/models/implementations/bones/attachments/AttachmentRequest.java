package zyx.opengl.models.implementations.bones.attachments;

import zyx.game.components.GameObject;

public class AttachmentRequest
{
	public GameObject child;
	public String attachmentPoint;

	public AttachmentRequest(GameObject child, String attachmentPoint)
	{
		this.child = child;
		this.attachmentPoint = attachmentPoint;
	}
}
