package zyx.opengl.models.implementations;

import java.util.ArrayList;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.renderers.PhysicsModelRenderer;

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
	public PhysicsModelRenderer createRenderer()
	{
		ArrayList<WorldModelMaterial> materials = getDefaultMaterials();
		WorldModelMaterial[] array = new WorldModelMaterial[subMeshCount];
		
		return new PhysicsModelRenderer(this, materials.toArray(array));
	}
	
	
}
