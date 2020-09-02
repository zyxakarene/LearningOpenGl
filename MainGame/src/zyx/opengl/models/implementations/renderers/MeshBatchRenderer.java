package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.MeshBatchModel;

public class MeshBatchRenderer extends MeshRenderer<WorldModelMaterial, MeshBatchModel>
{

	public MeshBatchRenderer(MeshBatchModel model, WorldModelMaterial defaultMaterial)
	{
		super(model, defaultMaterial);
	}

	public void setMeshBatchData(float[] batchData)
	{
		model.setMeshBatchData(batchData);
	}

}
