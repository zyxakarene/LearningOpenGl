package zyx.utils.interfaces;

import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.implementations.physics.PhysBox;

public interface IPhysbox
{
	public PhysBox getPhysbox();
	public Matrix4f getMatrix();
}
