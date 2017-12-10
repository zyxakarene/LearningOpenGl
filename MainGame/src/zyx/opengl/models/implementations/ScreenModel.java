package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.ScreenShader;
import zyx.opengl.textures.GameTexture;

public class ScreenModel extends AbstractModel
{

	public final ScreenShader shader;

	public ScreenModel(GameTexture texture)
	{
		super(Shader.SCREEN);

		shader = (ScreenShader) meshShader;

		GameTexture t = texture;
		float vertexData[] =
		{
			//Position			Texcoords
			0, 0,				t.x, t.y, // Top-left
			100, 0,				t.u, t.y, // Top-right
			100, -100,			t.u, t.v, // Bottom-right
			0, -100,			t.x, t.v  // Bottom-left
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
