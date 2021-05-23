package zyx.opengl.models.implementations.renderers;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.SkyboxModel;

public class SkyboxRenderer extends MeshRenderer<WorldModelMaterial, SkyboxModel>
{

	public SkyboxRenderer(SkyboxModel model, WorldModelMaterial[] defaultMaterials)
	{
		super(model, defaultMaterials);
	}

}
