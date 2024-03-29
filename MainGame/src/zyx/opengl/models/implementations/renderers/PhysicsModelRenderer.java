package zyx.opengl.models.implementations.renderers;

import zyx.opengl.GLUtils;
import zyx.opengl.materials.impl.WorldModelMaterial;
import zyx.opengl.models.implementations.WorldModel;

public class PhysicsModelRenderer extends WorldModelRenderer
{
	public PhysicsModelRenderer(WorldModel model, int meshIndex, WorldModelMaterial material)
	{
		super(model, meshIndex, material);
	}
	
	@Override
	protected void onPreDraw()
	{
		super.onPreDraw();
		
		GLUtils.setWireframe(true);
	}

	@Override
	public void draw()
	{
		super.draw();
		
		GLUtils.setWireframe(false);
	}
}
