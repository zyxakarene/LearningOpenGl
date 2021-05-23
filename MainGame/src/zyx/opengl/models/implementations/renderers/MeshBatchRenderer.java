package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.MeshBatchModel;

public class MeshBatchRenderer extends MeshRenderer<WorldModelMaterial, MeshBatchModel>
{

	public MeshBatchRenderer(MeshBatchModel model, WorldModelMaterial[] defaultMaterials)
	{
		super(model, defaultMaterials);
	}

	public void setMeshBatchData(int index, float[] batchData)
	{
		model.setMeshBatchData(index, batchData);
	}

}
