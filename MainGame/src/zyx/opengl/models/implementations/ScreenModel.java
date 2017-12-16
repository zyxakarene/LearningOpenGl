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
			//x				y					Texcoords
			0,				0,					t.x, t.y, // Top-left
			texture.width,	0,					t.u, t.y, // Top-right
			texture.width,	-texture.height,	t.u, t.v, // Bottom-right
			0,				-texture.height,	t.x, t.v  // Bottom-left
		};

		int elementData[] =
		{
			2, 1, 0,
			0, 3, 2
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
