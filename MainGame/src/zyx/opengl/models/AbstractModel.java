package zyx.opengl.models;

import zyx.opengl.materials.Material;
import zyx.opengl.models.implementations.renderers.wrappers.MeshWrapper;
import zyx.utils.interfaces.IDisposeable;

public abstract class AbstractModel<TMaterial extends Material> implements IDisposeable
{

	protected AbstractModel()
	{
	}
	
	protected abstract void createObjects();
	
	public abstract MeshWrapper createWrapper();

	protected final void addAttribute(int vbo, int shaderProgram, String attributeName, int components, int stride, int offset)
	{
		ModelUtils.bindBufferObject_Array(vbo);
		ModelUtils.addAttribute(shaderProgram, attributeName, components, stride, offset);
	}

	public abstract void draw();
	
	protected abstract void setupAttributes();

	protected boolean canDraw()
	{
		return true;
	}
	
}
