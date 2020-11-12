package zyx.utils.interfaces;

import org.lwjgl.util.vector.Matrix4f;
import zyx.engine.components.world.WorldObject;
import zyx.opengl.models.implementations.physics.PhysBox;

public interface IPhysbox extends IPositionable
{
	public PhysBox getPhysbox();
	public Matrix4f getMatrix();
	public Matrix4f getBoneMatrix(int boneId);
	public WorldObject getWorldObject();
}
