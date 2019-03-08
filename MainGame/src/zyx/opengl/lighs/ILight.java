package zyx.opengl.lighs;

import org.lwjgl.util.vector.Vector3f;

public interface ILight
{
	public float getIntensity();
	public Vector3f getLightPosition(Vector3f out);
	public int getColor();
}
