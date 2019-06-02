package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public class SkyboxModel extends AbstractModel
{
	private AbstractTexture originalTexture;
			
	public SkyboxModel(LoadableWorldModelVO vo)
	{
		super(Shader.SKYBOX);

		originalTexture = vo.gameTexture;
		
		setVertexData(vo.vertexData, vo.elementData);
		setTexture(vo.gameTexture);
	}

	public void setSkyboxTexture(AbstractTexture texture)
	{
		setTexture(texture);
	}
	
	public void removeSkyboxTexture()
	{
		setTexture(originalTexture);
	}
	
	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawWorld();
	}
	
	@Override
	protected void setupAttributes()
	{
		addAttribute("position", 3, 12, 0);
		addAttribute("texcoord", 2, 12, 6);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		originalTexture = null;
	}
}
