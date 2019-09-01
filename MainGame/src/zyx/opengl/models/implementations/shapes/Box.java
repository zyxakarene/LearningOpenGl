package zyx.opengl.models.implementations.shapes;

import zyx.game.components.SimpleMesh;
import zyx.opengl.GLUtils;
import zyx.opengl.textures.AbstractTexture;

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

	public void setTexture(AbstractTexture tex)
	{
		model.setDiffuse(tex);
	}
	
	@Override
	public void load(String resource)
	{
		throw new IllegalArgumentException("Do not load the Box instance");
	}

	public void doDraw()
	{
		draw();
	}
}
