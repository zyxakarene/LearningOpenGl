package zyx.game.components.screen;

import org.lwjgl.util.vector.Vector2f;
import zyx.utils.interfaces.IDrawable;
import zyx.utils.interfaces.IPositionable2D;

public abstract class DisplayObject implements IDrawable, IPositionable2D
{

	public Vector2f position;
	public float rotation;
	public Vector2f scale;

	public DisplayObject()
	{
		position = new Vector2f(0, 0);
		rotation = 0f;
		scale = new Vector2f(1, 1);
	}

	public abstract float getWidth();

	public abstract float getHeight();

	public float getScaleX()
	{
		return scale.x;
	}

	public float getScaleY()
	{
		return scale.y;
	}

	@Override
	public Vector2f getPosition()
	{
		return position;
	}

	@Override
	public float getRotation()
	{
		return rotation;
	}

	
}
