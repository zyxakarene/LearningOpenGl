package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.textures.AbstractTexture;

public class ScreenModel extends AbstractModel
{

	public final ScreenShader shader;

	public ScreenModel(AbstractTexture texture)
	{
		super(Shader.SCREEN);

		shader = (ScreenShader) meshShader;

		AbstractTexture t = texture;
		float w = t.getWidth();
		float h = t.getHeight();
		float vertexData[] =
		{
			//x		y		Texcoords
			0,		0,		t.x, t.y, // Top-left
			w,		0,		t.u, t.y, // Top-right
			w,		-h,		t.u, t.v, // Bottom-right
			0,		-h,		t.x, t.v  // Bottom-left
		};

		int elementData[] =
		{
			2, 1, 0,
			0, 3, 2
		};

		setVertexData(vertexData, elementData);
		setTexture(texture);
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
		addAttribute("position", 2, 4, 0);
		addAttribute("texcoord", 2, 4, 2);
	}
}
