package zyx.engine.components.screen.base.generic;

import zyx.engine.components.screen.base.DisplayObject;

public class Strut extends DisplayObject
{
	public static Strut width(float width)
	{
		return new Strut(width, 0);
	}
	
	public static Strut height(float height)
	{
		return new Strut(0, height);
	}
	
	public static Strut combined(float width, float height)
	{
		return new Strut(width, height);
	}
	
	private float width;
	private float height;
	
	private Strut(float width, float height)
	{
		this.width = width;
		this.height = height;
	}

	@Override
	public float getWidth()
	{
		return width;
	}

	@Override
	public float getHeight()
	{
		return height;
	}

	@Override
	protected void onSetWidth(float value)
	{
		width = value;
	}

	@Override
	protected void onSetHeight(float value)
	{
		height = value;
	}

	@Override
	protected void onDraw()
	{
	}
}
