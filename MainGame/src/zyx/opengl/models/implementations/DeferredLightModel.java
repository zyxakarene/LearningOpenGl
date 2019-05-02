package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.shaders.implementations.LightingPassShader;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public class DeferredLightModel extends AbstractModel
{
	public final LightingPassShader shader;
	
	public DeferredLightModel()
	{
		super(Shader.DEFERED_LIGHT_PASS);

		shader = (LightingPassShader) meshShader;

		setTexture(null);
	}
	
	public void addVertexData(float x, float y, float width, float height, AbstractTexture tex)
	{
		AbstractTexture t = tex;
		float w = width;
		float h = height;
		float vertexData[] =
		{
			//x			y			Texcoords
			x,			-y,			t.x, t.y,	// Top-left
			x + w,		-y,			t.u, t.y,	// Top-right
			x + w,		-h - y,		t.u, t.v,	// Bottom-right
			x,			-h - y,		t.x, t.v,	// Bottom-left
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
