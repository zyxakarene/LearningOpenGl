package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.shaders.implementations.SkyboxShader;

public class SkyboxModel extends AbstractModel
{

	private SkyboxShader shader;

	public SkyboxModel(LoadableWorldModelVO vo)
	{
		super(Shader.SKYBOX);
		this.shader = (SkyboxShader) meshShader;

		setVertexData(vo.vertexData, vo.elementData);
		setTexture(vo.gameTexture);
	}

	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawWorld();
	}

	@Override
	public void draw()
	{
		super.draw();
	}

	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 3, 12, 0);
		addAttribute("normals", 3, 12, 3);
		addAttribute("texcoord", 2, 12, 6);
		addAttribute("indexes", 2, 12, 8);
		addAttribute("weights", 2, 12, 10);
	}
	
	@Override
	public void dispose()
	{
		super.dispose();

		shader = null;
	}

}
