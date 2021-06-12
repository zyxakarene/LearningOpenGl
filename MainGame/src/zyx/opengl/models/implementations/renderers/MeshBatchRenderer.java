package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.MeshBatchModel;

public class MeshBatchRenderer extends MeshRenderer<WorldModelMaterial, MeshBatchModel>
{

	private float[] batchData;

	public MeshBatchRenderer(MeshBatchModel model, int meshIndex, WorldModelMaterial defaultMaterial)
	{
		super(model, meshIndex, defaultMaterial);
	}

	public void setMeshBatchData(float[] batchData)
	{
		this.batchData = batchData;
	}

	@Override
	protected void onPreDraw()
	{
		if (batchData != null)
		{
			model.setMeshBatchData(batchData);
		}
	}
}
