package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.DepthMaterial;
import zyx.opengl.shaders.AbstractShader;

abstract class ModelShaderData<TShader extends AbstractShader, TShadowShader extends AbstractShader>
{
	TShader shader;
	TShadowShader shadowShader;
	DepthMaterial shadowMaterial;
	int boneCount;

	ModelShaderData()
	{
	}
}
