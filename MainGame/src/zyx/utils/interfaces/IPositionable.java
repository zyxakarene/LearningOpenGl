package zyx.utils.interfaces;

import org.lwjgl.util.vector.Vector3f;

public interface IPositionable
{
	public Vector3f getPosition();
	public Vector3f getRotation();
	public Vector3f getWorldPosition(Vector3f out);
}
