package zyx.utils.interfaces;

import org.lwjgl.util.vector.Vector3f;

public interface IPositionable
{
	public void setDir(boolean local, Vector3f dir);
	public void lookAt(float x, float y, float z);
	
	public void setPosition(boolean local, Vector3f pos);
	public void setScale(Vector3f scale);
	public void setScale(float x, float y, float z);
	public void setRotation(Vector3f rot);
	
	public Vector3f getPosition(boolean local, Vector3f out);
	public Vector3f getRotation(boolean local, Vector3f out);
	public Vector3f getScale(boolean local, Vector3f out);
	public Vector3f getDir(boolean local, Vector3f out);
	public Vector3f getUp(boolean local, Vector3f out);
	public Vector3f getRight(boolean local, Vector3f out);
	
	public void setX(float x);
	public void setY(float y);
	public void setZ(float z);
}
