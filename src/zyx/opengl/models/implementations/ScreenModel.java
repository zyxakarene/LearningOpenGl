package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ScreenShader;

public class ScreenModel extends AbstractModel
{

	public final ScreenShader shader;

	public ScreenModel(String texture)
	{
		super(Shader.SCREEN);

		shader = (ScreenShader) meshShader;

		float vertexData[] =
		{
			//  Position      Color             Texcoords
			0, 0, 0, 0, // Top-left
			100, 0, 1, 0, // Top-right
			100, -100, 1, 1, // Bottom-right
			0, -100, 0, 1  // Bottom-left
		};

		int elementData[] =
		{
			0, 1, 2,
			2, 3, 0
		};

		setVertexData(vertexData, elementData);
		setTexture(texture);
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 4, 0);
		addAttribute("texcoord", 2, 4, 2);
	}

}
