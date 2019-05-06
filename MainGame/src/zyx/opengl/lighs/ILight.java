package zyx.opengl.lighs;

import org.lwjgl.util.vector.Vector3f;

public interface ILight
{
	public int getPower();
	public Vector3f getLightPosition(Vector3f out);
	public int getColor();
	public Vector3f getColorVector(Vector3f out);
}
