package zyx.opengl.models.implementations.shapes;

import zyx.game.components.SimpleMesh;

public class Sphere extends SimpleMesh
{

	public Sphere()
	{
		this(10);
	}

	public Sphere(float radius)
	{
		setScale(radius, radius, radius);
		super.load("mesh.simple.sphere");
	}

	@Override
	public void load(String resource)
	{
		throw new IllegalArgumentException("Do not load the Sphere instance");
	}
}
