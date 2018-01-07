package zyx.engine.components.screen;

import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.Text;

public class Textfield extends DisplayObject
{

	private final Text glText;
	private String text;

	public Textfield(BitmapFont font)
	{
		this.glText = new Text(font);
	}

	public void setText(String text)
	{
		this.text = text;
		glText.setText(text);
	}

	public String getText()
	{
		return text;
	}

	@Override
	public float getWidth()
	{
		return 0f;
	}

	@Override
	public float getHeight()
	{
		return 0f;
	}

	@Override
	void onDraw()
	{
		transform();
		shader.upload();
		glText.draw();
	}

	@Override
	public void setWidth(float value)
	{
	}

	@Override
	public void setHeight(float value)
	{
	}

}