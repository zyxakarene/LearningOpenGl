package zyx.utils.interfaces;

import org.lwjgl.util.vector.Matrix4f;
import zyx.engine.utils.worldpicker.ClickedInfo;
import zyx.opengl.models.implementations.physics.PhysBox;

public interface IPhysbox
{
	public PhysBox getPhysbox();
	public Matrix4f getMatrix();
	public Matrix4f getBoneMatrix(int boneId);
}
