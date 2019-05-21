package zyx.opengl.lighs;

import org.lwjgl.util.vector.Vector3f;

public interface ISun
{
	public void getSunDirection(Vector3f out);

	public void calculateShadows();
}
