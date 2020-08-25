package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.AbstractModel;
import zyx.opengl.models.DebugDrawCalls;

public class SkyboxModel extends AbstractModel<WorldModelMaterial>
{

	private static final int POSITION_LENGTH = 3;
	private static final int NORMALS_LENGTH = 3;
	private static final int TEX_COORDS_LENGTH = 2;
	private static final int BONE_LENGTH = 2;

	private int boneCount;

	public SkyboxModel(LoadableWorldModelVO vo)
	{
		super(vo.material);
		boneCount = vo.boneCount;
		setup();

		setVertexData(vo.vertexData, vo.elementData);
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
}
