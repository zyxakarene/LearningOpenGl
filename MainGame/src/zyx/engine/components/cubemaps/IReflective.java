package zyx.engine.components.cubemaps;

import org.lwjgl.util.vector.Vector3f;

public interface IReflective
{
	public Vector3f getPosition(boolean local, Vector3f out);

	public void enableCubemaps();
	public void disableCubemaps();
	
	public void setCubemapIndex(int index);
}
