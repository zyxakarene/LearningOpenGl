package zyx.utils.interfaces;

import org.lwjgl.util.vector.Vector2f;

public interface IPositionable2D
{

	public void setPosition(boolean local, Vector2f pos);
	public void setScale(Vector2f scale);
	public void setRotation(float rotation);

	public Vector2f getPosition(boolean local, Vector2f out);
	public float getRotation(boolean local);
	public Vector2f getScale(boolean local, Vector2f out);

}
