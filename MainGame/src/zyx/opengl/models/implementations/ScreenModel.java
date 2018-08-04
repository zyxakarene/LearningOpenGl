package zyx.opengl.models.implementations;

import java.nio.FloatBuffer;
import org.lwjgl.util.vector.Vector4f;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.BufferWrapper;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.textures.AbstractTexture;

public class ScreenModel extends AbstractModel
{

	public final ScreenShader shader;

	public ScreenModel(AbstractTexture texture, float w, float h, Vector4f colors)
	{
		super(Shader.SCREEN);

		shader = (ScreenShader) meshShader;

		AbstractTexture t = texture;
		Vector4f c = colors;
		float vertexData[] =
		{
			//x		y		Texcoords	//Colors
			0,		0,		t.x, t.y,	c.x, c.y, c.z, c.w, // Top-left
			w,		0,		t.u, t.y,	c.x, c.y, c.z, c.w, // Top-right
			w,		-h,		t.u, t.v,	c.x, c.y, c.z, c.w, // Bottom-right
			0,		-h,		t.x, t.v,	c.x, c.y, c.z, c.w,// Bottom-left
		};

		int elementData[] =
		{
			2, 1, 0,
			0, 3, 2
		};

		setVertexData(vertexData, elementData);
		setTexture(texture);
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

		for (int i = 0; i < 4; i++)
		{
			long base = offsetPerVertex * i;
			long offset = base + (Float.BYTES * 4);
			updateVertexSubData(buffer, offset);
		}
	}
	
	public float getWidth()
	{
		return getTexture().getWidth();
	}
	
	public float getHeight()
	{
		return getTexture().getHeight();
	}
	
	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 8, 0);
		addAttribute("texcoord", 2, 8, 2);
		addAttribute("color", 4, 8, 4);
	}
}
