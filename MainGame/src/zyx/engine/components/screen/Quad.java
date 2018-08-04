package zyx.engine.components.screen;

import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.models.implementations.ScreenModel;
import zyx.opengl.textures.ColorTexture;
import zyx.utils.Color;

public class Quad extends DisplayObject
{

	private Vector4f colors;
	private float width;
	private float height;

	private ScreenModel model;

	public Quad(float width, float height, int color)
	{
		colors = Color.toVector(color);
		colors.w = 1;

		this.width = width;
		this.height = height;
		
		this.scale.x = width;
		this.scale.y = height;
		
		ColorTexture texture = new ColorTexture(color);
		model = new ScreenModel(texture, 1, 1, colors);
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
	public void setWidth(float value)
	{
		width = value;
	}

	@Override
	public void setHeight(float value)
	{
		height = value;
	}

	@Override
	void onDraw()
	{
		transform();
		shader.upload();

		model.draw();
	}

}
