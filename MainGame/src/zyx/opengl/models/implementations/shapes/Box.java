package zyx.opengl.models.implementations.shapes;

import zyx.game.components.SimpleMesh;

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
	public void load(String resource)
	{
		throw new IllegalArgumentException("Do not load the Box instance");
	}
}
