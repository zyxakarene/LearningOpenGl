package zyx.opengl.models.implementations.bones.attachments;

import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.utils.interfaces.IPositionable;

public class Attachment
{
	public Joint joint;
	public WorldModel parent;
	public WorldModel child;
	public AnimationController animations;
	public IPositionable position;
}
