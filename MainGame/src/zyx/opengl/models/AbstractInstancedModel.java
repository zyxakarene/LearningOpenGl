package zyx.opengl.models;

import zyx.opengl.materials.Material;

public abstract class AbstractInstancedModel<TMaterial extends Material> extends AbstractModel<TMaterial>
{

	private int instanceVbo;
	
	private int instanceCount;

	public AbstractInstancedModel(TMaterial material)
	{
		super(material);
		setup();
	}

	@Override
	protected void createObjects()
	{
		super.createObjects();
		instanceVbo = ModelUtils.generateBufferObject();
	}

	protected void setInstanceData(float[] data, int count)
	{
		ModelUtils.bindBufferObject_Array(instanceVbo);
		ModelUtils.fillVBO_Dynamic(data);
		instanceCount = count;
	}
	
	protected final void addInstanceAttribute(String attributeName, int components, int stride, int offset)
	{
		ModelUtils.bindBufferObject_Array(instanceVbo);
		ModelUtils.addInstanceAttribute(meshShader.program, attributeName, components, stride, offset);
	}
	
	@Override
	public void draw(Material material)
	{
		if (elementCount > 0 && instanceCount > 0)
		{
			if (canDraw())
			{
				material.bind();
				
				ModelUtils.drawInstancedElements(vao, elementCount, instanceCount);
			}
		}
	}

	@Override
	protected boolean canDraw()
	{
		return DebugDrawCalls.canDrawWorld();
	}
	
	@Override
	public void dispose()
	{
		ModelUtils.disposeBuffer(instanceVbo);
		super.dispose();
	}

}
