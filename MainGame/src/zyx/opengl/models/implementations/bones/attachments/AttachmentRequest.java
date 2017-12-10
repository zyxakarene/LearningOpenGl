package zyx.opengl.models.implementations.bones.attachments;

import zyx.game.components.WorldObject;

public class AttachmentRequest
{
	public WorldObject child;
	public String attachmentPoint;

	public AttachmentRequest(WorldObject child, String attachmentPoint)
	{
		this.child = child;
		this.attachmentPoint = attachmentPoint;
	}
}
