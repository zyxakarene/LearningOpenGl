package zyx.opengl.models.implementations.shapes;

import zyx.engine.resources.impl.meshes.MeshResource;
import zyx.game.components.SimpleMesh;
import zyx.opengl.materials.BlendMode;
import zyx.opengl.materials.RenderQueue;
import zyx.opengl.materials.ZTest;
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
		setScale(width, depth, height);
		super.load("mesh.simple.box");
	}

	@Override
	public void onResourceReady(MeshResource resource)
	{
		super.onResourceReady(resource);
	
		WorldModelMaterial mat = cloneMaterial();
		mat.queue = RenderQueue.ALPHA;
		mat.blend = BlendMode.NORMAL;
		mat.zWrite = ZWrite.ENABLED;
		mat.zTest = ZTest.ALWAYS;
	}
	
	@Override
	public void load(String resource)
	{
		throw new IllegalArgumentException("Do not load the Box instance");
	}
}
