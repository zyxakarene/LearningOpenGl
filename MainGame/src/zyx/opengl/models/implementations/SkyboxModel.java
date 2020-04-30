package zyx.opengl.models.implementations;

import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.shaders.implementations.Shader;
import zyx.opengl.textures.AbstractTexture;

public class SkyboxModel extends AbstractModel
{

	private static final int POSITION_LENGTH = 3;
	private static final int NORMALS_LENGTH = 3;
	private static final int TEX_COORDS_LENGTH = 2;
	private static final int BONE_LENGTH = 2;

	private AbstractTexture originalTexture;
	private int boneCount;

	public SkyboxModel(LoadableWorldModelVO vo)
	{
		super(Shader.SKYBOX);
		boneCount = vo.boneCount;
		setup();

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
		int stride = POSITION_LENGTH + NORMALS_LENGTH + TEX_COORDS_LENGTH + (BONE_LENGTH * boneCount);

		addAttribute("position", POSITION_LENGTH, stride, 0);
//		addAttribute("normals", NORMALS_LENGTH, stride, 3);
		addAttribute("texcoord", TEX_COORDS_LENGTH, stride, 6);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		originalTexture = null;
	}
}
