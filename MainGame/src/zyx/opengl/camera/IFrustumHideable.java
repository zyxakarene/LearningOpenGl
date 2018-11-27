package zyx.opengl.camera;

import org.lwjgl.util.vector.Vector3f;

public interface IFrustumHideable
{
	public float getRadius();
	public void getCenter(Vector3f out);
}
