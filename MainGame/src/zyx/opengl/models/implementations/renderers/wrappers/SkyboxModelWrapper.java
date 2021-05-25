package zyx.opengl.models.implementations.renderers.wrappers;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.SkyboxModel;
import zyx.opengl.models.implementations.renderers.SkyboxRenderer;

public class SkyboxModelWrapper extends MeshWrapper<WorldModelMaterial, SkyboxModel, SkyboxRenderer>
{
	public SkyboxModelWrapper(SkyboxRenderer[] renderers, SkyboxModel model)
	{
		super(renderers, model);
	}
}
