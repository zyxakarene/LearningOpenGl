package zyx.opengl.textures.bitmapfont;

import java.nio.FloatBuffer;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.BufferWrapper;
import zyx.opengl.shaders.implementations.Shader;

public class Text extends AbstractModel
{

	private BitmapFont font;
	private int characterCount;
	private int vertexCount;

	private float width;
	private float height;
	
	public Text(BitmapFont font)
	{
		super(Shader.SCREEN);
		this.font = font;

		setTexture(font.texture);
	}

	public void setText(String text, Vector4f color)
	{
		TextGenerator generator = new TextGenerator(font.fontFile, color);

		characterCount = text.length();
		char character;
		for (int i = 0; i < characterCount; i++)
		{
			character = text.charAt(i);
			generator.addCharacter(character);
		}

		float[] vertexData = generator.getVertexData();
		int[] elementData = generator.getElementData();

		vertexCount = generator.getVertexCount();
		width = generator.getWidth();
		height = generator.getHeight();
				
		bindVao();
		setVertexData(vertexData, elementData);
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 8, 0);
		addAttribute("texcoord", 2, 8, 2);
		addAttribute("color", 4, 8, 4);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		font = null;
	}

	public float getWidth()
	{
		return width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setColors(Vector4f colors)
	{
		bindVao();

		float vertexData[] = new float[]
		{
			colors.x, colors.y, colors.z, colors.w
		};
		FloatBuffer buffer = BufferWrapper.toBuffer(vertexData);

		int offsetPerVertex = Float.BYTES * 8;

		for (int i = 0; i < vertexCount; i++)
		{
			long base = offsetPerVertex * i;
			long offset = base + (Float.BYTES * 4);
			updateVertexSubData(buffer, offset);
		}
	}
}
