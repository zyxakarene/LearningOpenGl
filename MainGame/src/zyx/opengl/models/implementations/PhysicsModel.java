package zyx.opengl.models.implementations;

import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.renderers.PhysicsModelRenderer;
import zyx.opengl.models.implementations.renderers.wrappers.WorldModelWrapper;

public class PhysicsModel extends WorldModel
{

	public PhysicsModel(LoadablePhysicsModelVO vo)
	{
		super(vo);
	}
	
	@Override
	public void draw(int index, WorldModelMaterial material)
	{
		modelData[0].meshShader.bind();
		modelData[0].meshShader.upload();
		
		super.draw(index, material);
	}
	
	@Override
	public WorldModelWrapper createWrapper()
	{
		PhysicsModelRenderer[] array = new PhysicsModelRenderer[subMeshCount];
		for (int i = 0; i < subMeshCount; i++)
		{
			WorldModelMaterial material = getDefaultMaterial(i);
			array[i] = new PhysicsModelRenderer(this, i, material);
		}

		return new WorldModelWrapper(array, this);
	}
}
