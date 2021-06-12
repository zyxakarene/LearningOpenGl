package zyx.opengl.models.implementations.renderers.wrappers;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.MeshBatchModel;
import zyx.opengl.models.implementations.renderers.MeshBatchRenderer;

public class MeshBatchModelWrapper extends MeshWrapper<WorldModelMaterial, MeshBatchModel, MeshBatchRenderer>
{

	public MeshBatchModelWrapper(MeshBatchRenderer[] renderers, MeshBatchModel model)
	{
		super(renderers, model);
	}
	
	public void setMeshBatchData(float[] batchData)
	{
		for (MeshBatchRenderer renderer : renderers)
		{
			renderer.setMeshBatchData(batchData);
		}
	}
}
