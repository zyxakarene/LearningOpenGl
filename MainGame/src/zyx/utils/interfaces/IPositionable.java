package zyx.utils.interfaces;

import org.lwjgl.util.vector.Vector3f;

public interface IPositionable
{
	public void setPosition(Vector3f pos);
	public void setRotation(Vector3f rot);
	public void setDir(Vector3f dir);
	public void setScale(Vector3f scale);
	
	public Vector3f getPosition(boolean local, Vector3f out);
	public Vector3f getRotation(boolean local, Vector3f out);
	public Vector3f getScale(boolean local, Vector3f out);
	public Vector3f getDir(boolean local, Vector3f out);
	public Vector3f getUp(boolean local, Vector3f out);
	public Vector3f getRight(boolean local, Vector3f out);
}
