package zyx.game.components.screen;

import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.textures.bitmapfont.BitmapFont;
import zyx.opengl.textures.bitmapfont.Text;
import zyx.utils.FloatMath;

public class Textfield extends DisplayObject
{

	private final BitmapFont font;
	private final Text glText;
	private String text;

	public Textfield(BitmapFont font)
	{
		this.font = font;
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
	protected void draw()
	{
		transform();
		shader.upload();
		glText.draw();
		
		System.out.println(ScreenShader.MATRIX_MODEL);
		System.out.println(FloatMath.toRadians(-rotation));
	}

}
