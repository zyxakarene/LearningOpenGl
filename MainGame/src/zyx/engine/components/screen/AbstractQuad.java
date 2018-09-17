package zyx.engine.components.screen;

import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.models.implementations.ScreenModel;

public abstract class AbstractQuad extends DisplayObject
{

	protected Vector4f colors;
	protected float originalWidth;
	protected float originalHeight;
	protected ScreenModel model;
	protected boolean loaded;
	
	public AbstractQuad()
	{
		colors = new Vector4f(1, 1, 1, 1);
	}

	public void setColor(Vector4f color)
	{
		colors.set(color);
		updateMesh();
	}

	public void setColor(float r, float g, float b)
	{
		colors.set(r, g, b);
		updateMesh();
	}

	public void setAlpha(float a)
	{
		colors.w = a;
		updateMesh();
	}

	private void updateMesh()
	{
		if (loaded)
		{
			model.setColors(colors);
		}
	}
	
	@Override
	public float getWidth()
	{
		if (loaded)
		{
			return model.getWidth() * getScale(true, HELPER_VEC2).x;
		}

		return 0;
	}

	@Override
	public float getHeight()
	{
		if (loaded)
		{
			return model.getHeight() * getScale(true, HELPER_VEC2).y;
		}

		return 0;
	}

	@Override
	public void setWidth(float value)
	{
		if (loaded)
		{
			getScale(true, HELPER_VEC2);
			setScale(value / originalWidth, HELPER_VEC2.y);
		}
	}

	@Override
	public void setHeight(float value)
	{
		if (loaded)
		{
			getScale(true, HELPER_VEC2);
			setScale(HELPER_VEC2.x, value / originalHeight);
		}
	}

	@Override
	void onDraw()
	{
		if (loaded)
		{
			model.draw();
		}
	}
}
