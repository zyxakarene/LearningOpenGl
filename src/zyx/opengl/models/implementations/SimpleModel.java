package zyx.opengl.models.implementations;

import java.util.Arrays;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;

public class SimpleModel extends AbstractModel
{

	public SimpleModel()
	{
		super(Shader.BASE);

		float vertexData[] =
		{
			//Position		//Texcoords
			-0.5f, 0.5f,	0.0f, 0.0f, // Top-left
			0.5f, 0.5f,		1.0f, 0.0f, // Top-right
			0.5f, -0.5f,	1.0f, 1.0f, // Bottom-right
			-0.5f, -0.5f,	0.0f, 1.0f  // Bottom-left
		};

		int elementData[] =
		{
			0, 1, 2,
			2, 3, 0
		};

		setVertexData(vertexData, elementData);
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 2, 4, 0);
		addAttribute("texcoord", 2, 4, 2);
	}
}
