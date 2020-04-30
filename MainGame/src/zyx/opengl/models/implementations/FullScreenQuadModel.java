package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public class FullScreenQuadModel extends AbstractModel
{
	public FullScreenQuadModel(Shader shader, AbstractTexture... textures)
	{
		super(shader);

		setup();
		
		setTextures(textures);
		setVertexData();
	}
	
	private void setVertexData()
	{
		float vertexData[] =
		{
			//x		y		Texcoords
			-1,		1,		0, 1,	// Top-left
			1,		1,		1, 1,	// Top-right
			1,		-1,		1, 0,	// Bottom-right
			-1,		-1,		0, 0,	// Bottom-left
		};
		
		int elementData[] =
		{
			2, 1, 0,
			0, 3, 2
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
