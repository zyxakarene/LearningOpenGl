package zyx.opengl.models.implementations.bones.attachments;

import zyx.game.components.WorldObject;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.utils.interfaces.IPositionable;

public class Attachment
{
	public Joint joint;
	public WorldObject parent;
	public WorldObject child;
	public AnimationController animations;
	public IPositionable position;
}
