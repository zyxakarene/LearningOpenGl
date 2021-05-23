package zyx.opengl.models.implementations;

import zyx.opengl.materials.Material;
import zyx.opengl.models.AbstractSingleModel;
import zyx.opengl.models.implementations.renderers.MeshRenderer;

public class FullScreenQuadModel extends AbstractSingleModel
{
	public FullScreenQuadModel(Material material)
	{
		super(material);

		setup();
		
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

	@Override
	public MeshRenderer createRenderer()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
