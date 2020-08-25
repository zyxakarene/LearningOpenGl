package zyx.engine.components.screen.image;

import zyx.engine.components.screen.base.DisplayObject;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.materials.impl.ScreenModelMaterial;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.utils.Color;

public abstract class AbstractQuad extends DisplayObject
{

	protected float originalWidth;
	protected float originalHeight;
	protected ScreenModel model;
	protected boolean loaded;
	
	protected final ScreenModelMaterial material;
	
	public AbstractQuad()
	{
		material = new ScreenModelMaterial(Shader.SCREEN);
		
		originalWidth = 0;
		originalHeight = 0;
	}

	public void setColor(Vector4f color)
	{
		material.color.set(color);
		material.alpha = color.w;
		updateMesh();
	}

	public void setColor(int color)
	{
		Color.toVector(color, material.color);
		updateMesh();
	}

	public void setColor(float r, float g, float b)
	{
		material.color.set(r, g, b);
		updateMesh();
	}

	public void setAlpha(float a)
	{
		material.alpha = a;
		updateMesh();
	}

	public int getColor()
	{
		return Color.toInt(material.color);
	}
	
	private void updateMesh()
	{
		if (loaded)
		{
			model.updateColors();
		}
	}
	
	@Override
	public float getWidth()
	{
		if (loaded)
		{
			return model.getWidth() * getScale(true, HELPER_VEC2).x;
		}

		return originalWidth;
	}

	@Override
	public float getHeight()
	{
		if (loaded)
		{
			return model.getHeight() * getScale(true, HELPER_VEC2).y;
		}

		return originalHeight;
	}

	public void setSize(float x, float y)
	{
		if (loaded)
		{
			setScale(x / originalWidth, y / originalHeight);
		}
		else
		{
			originalWidth = x;
			originalHeight = y;
		}
	}

	@Override
	public void setWidth(float value)
	{
		if (loaded)
		{
			getScale(true, HELPER_VEC2);
			setScale(value / originalWidth, HELPER_VEC2.y);
		}
		else
		{
			originalWidth = value;
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
		else
		{
			originalHeight = value;
		}
	}

	protected void onModelCreated()
	{
		loaded = true;
		
		float w = getWidth();
		float h = getHeight();

		if (originalWidth != 0)
		{
			float newW = originalWidth;
			originalWidth = w;
			setWidth(newW);
		}
		
		if (originalHeight != 0)
		{
			float newH = originalHeight;
			originalHeight = h;
			setHeight(newH);
		}
		
		originalWidth = w;
		originalHeight = h;
	}
	
	public boolean isLoaded()
	{
		return loaded;
	}
	
	@Override
	protected void onDraw()
	{
		if (loaded)
		{
			model.draw();
		}
	}
}
