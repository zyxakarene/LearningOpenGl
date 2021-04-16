package zyx.opengl.models.implementations.shapes;

import zyx.engine.resources.impl.meshes.MeshResource;
import zyx.game.components.SimpleMesh;
import zyx.opengl.materials.BlendMode;
import zyx.opengl.materials.RenderQueue;
import zyx.opengl.materials.ZWrite;
import zyx.opengl.materials.impl.WorldModelMaterial;

public class Box extends SimpleMesh
{

	public Box()
	{
		this(10, 10, 10);
	}

	public Box(float width, float depth, float height)
	{
		if (Math.random() > 0.5)
		{
			setScale(width, depth, height);
			super.load("mesh.simple.box");
		}
		else
		{
			setScale(0.25f, 0.25f, 0.25f);
			super.load("mesh.dragon");
		}
	}

	@Override
	public void onResourceReady(MeshResource resource)
	{
		super.onResourceReady(resource);
		
		if (Math.random() >= 0.5f)
		{
 			WorldModelMaterial mat = renderer.cloneMaterial();
			mat.priority = (int) (1000 * Math.random());
			mat.queue = RenderQueue.TRANSPARENT;
			mat.blend = BlendMode.ALPHA;
			mat.zWrite = ZWrite.DISABLED;
		}
	}
	
	@Override
	public void load(String resource)
	{
		throw new IllegalArgumentException("Do not load the Box instance");
	}
}
