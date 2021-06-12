package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.AbstractMultiModel;
import zyx.opengl.models.DebugDrawCalls;
import zyx.opengl.models.implementations.renderers.SkyboxRenderer;
import zyx.opengl.models.implementations.renderers.wrappers.*;

public class SkyboxModel extends AbstractMultiModel<WorldModelMaterial>
{

	private static final int POSITION_LENGTH = 3;
	private static final int NORMALS_LENGTH = 3;
	private static final int TEX_COORDS_LENGTH = 2;
	private static final int BONE_LENGTH = 2;

	private int[] boneCounts;

	public SkyboxModel(LoadableWorldModelVO vo)
	{
		setSubMeshCount(vo.subMeshCount);
		boneCounts = new int[vo.subMeshCount];
		
		setDefaultMaterials(vo.getDefaultMaterials());
		
		createObjects();

		for (int i = 0; i < subMeshCount; i++)
		{
			AbstractLoadableSubMeshModelVO subMesh = vo.subMeshes[i];
			boneCounts[i] = subMesh.boneCount;
			setVertexData(i, subMesh.vertexData, subMesh.elementData);
		}
		
		setupAttributes();
	}

	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawWorld();
	}

	@Override
	protected void setupAttributes()
	{
		for (int i = 0; i < subMeshCount; i++)
		{
			bindVao(i);

			int stride = POSITION_LENGTH + NORMALS_LENGTH + TEX_COORDS_LENGTH + (BONE_LENGTH * boneCounts[i]);
			
			addAttribute(i, "position", POSITION_LENGTH, stride, 0);
	//		addAttribute(i, "normals", NORMALS_LENGTH, stride, 3);
			addAttribute(i, "texcoord", TEX_COORDS_LENGTH, stride, 6);
		}

	}

	@Override
	public SkyboxModelWrapper createWrapper()
	{
		SkyboxRenderer[] array = new SkyboxRenderer[subMeshCount];
		for (int i = 0; i < subMeshCount; i++)
		{
			WorldModelMaterial material = getDefaultMaterial(i);
			array[i] = new SkyboxRenderer(this, i, material);
		}

		return new SkyboxModelWrapper(array, this);
	}
}
